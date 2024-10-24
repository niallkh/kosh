package kosh.libs.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGatt.GATT_SUCCESS
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
import android.bluetooth.BluetoothStatusCodes
import android.os.Build
import co.touchlab.kermit.Logger
import kosh.libs.transport.Transport
import kosh.libs.transport.TransportException.CommunicationFailedException
import kosh.libs.transport.TransportException.ConnectionFailedException
import kosh.libs.transport.TransportException.DeviceDisconnectedException
import kosh.libs.transport.TransportException.ResponseTimeoutException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import kotlinx.io.readByteString
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.math.min
import kotlin.time.Duration.Companion.seconds
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

class AndroidBleConnection(
    private val config: BleConfig,
    private val dispatcher: CoroutineDispatcher,
) : Transport.Connection, BluetoothGattCallback() {

    private val logger = Logger.withTag("[K]AndroidBleConnection")

    private val readMutex = Mutex()
    private val writeMutex = Mutex()

    private lateinit var gatt: BluetoothGatt
    private val connected = MutableStateFlow(false)
    private val mtuDeferred = CompletableDeferred<Unit>()
    private val notificationDeferred = CompletableDeferred<Unit>()
    private val writeCharacteristic = CompletableDeferred<BluetoothGattCharacteristic>()
    private val readBuffer = Buffer()
    private var writeCont: Continuation<Unit>? = null

    override var writeMtu: Int = MTU_MIN

    @SuppressLint("MissingPermission")
    suspend fun initialize() {
        logger.d { "init()" }
        withTimeoutOrNull(10.seconds) {
            connected.first { it }
        } ?: throw ConnectionFailedException("Device not connected")

        withTimeoutOrNull(10.seconds) {
            check(gatt.discoverServices())
            writeCharacteristic.await()
            notificationDeferred.await()
            check(gatt.requestMtu(MTU_MAX))
            mtuDeferred.await()
        } ?: throw ConnectionFailedException("Initialization failed")
    }

    @SuppressLint("MissingPermission")
    override suspend fun write(data: ByteString) = withContext(dispatcher) {
        writeMutex.withLock {
            logger.v { "write(${data.size})" }
            if (!connected.value)
                throw DeviceDisconnectedException()

            val characteristic = writeCharacteristic.await()

            UnsafeByteStringOperations.withByteArrayUnsafe(data) {
                val writeType = WRITE_TYPE_DEFAULT

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    check(
                        gatt.writeCharacteristic(
                            characteristic, it, writeType
                        ) == BluetoothStatusCodes.SUCCESS
                    )
                } else {
                    characteristic.writeType = writeType
                    characteristic.value = it
                    check(gatt.writeCharacteristic(characteristic))
                }
            }

            withTimeoutOrNull(10.seconds) {
                suspendCancellableCoroutine<Unit> { cont -> writeCont = cont }
            } ?: throw ResponseTimeoutException()
        }
    }

    override suspend fun read(): ByteString = withContext(dispatcher) {
        readMutex.withLock {
            logger.v { "read" }

            while (readBuffer.size == 0L) {
                if (!connected.value)
                    throw DeviceDisconnectedException()
                delay(10)
            }

            readBuffer.readByteString()
        }
    }

    override fun close() {
        logger.v { "close" }
    }

    override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
        logger.v { "onConnectionStateChange($status, $newState)" }
        this.gatt = gatt
        connected.value = newState == BluetoothGatt.STATE_CONNECTED
    }

    override fun onMtuChanged(gatt: BluetoothGatt, mtu: Int, status: Int) {
        logger.v { "onMtuChanged($mtu, $status)" }
        this.gatt = gatt
        if (status == BluetoothStatusCodes.SUCCESS) {
            this.writeMtu = min(mtu, config.maxWriteMtu) - MTU_HEADER
            mtuDeferred.complete(Unit)
        } else {
            mtuDeferred.completeExceptionally(CommunicationFailedException("Failed to set MTU"))
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        logger.v { "onServicesDiscovered($status)" }
        this.gatt = gatt

        if (status != BluetoothStatusCodes.SUCCESS) {
            writeCharacteristic.completeExceptionally(CommunicationFailedException("Failed to discover services"))
            return
        }

        gatt.services.asSequence()
            .filter { it.uuid.toKotlinUuid() in config.serviceUuids }
            .flatMap { it.characteristics }
            .firstOrNull { it.uuid.toKotlinUuid() in config.charNotifyUuids }
            ?.let { enableNotification(it) }

        gatt.services.asSequence()
            .filter { it.uuid.toKotlinUuid() in config.serviceUuids }
            .flatMap { it.characteristics }
            .firstOrNull { it.uuid.toKotlinUuid() in config.charWriteUuids }
            ?.let { writeCharacteristic.complete(it) }
            ?: writeCharacteristic.completeExceptionally(CommunicationFailedException("Failed to find write characteristic"))
    }

    override fun onDescriptorWrite(
        gatt: BluetoothGatt,
        descriptor: BluetoothGattDescriptor?,
        status: Int,
    ) {
        logger.v { "onDescriptorWrite($status)" }
        this.gatt = gatt
        if (descriptor?.uuid?.toKotlinUuid() == CCC_UUID) {
            if (status == BluetoothStatusCodes.SUCCESS) {
                notificationDeferred.complete(Unit)
            } else {
                notificationDeferred.completeExceptionally(CommunicationFailedException("Failed to enable notifications"))
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableNotification(
        characteristic: BluetoothGattCharacteristic,
    ) {
        check(gatt.setCharacteristicNotification(characteristic, true))
        val cccDescriptor = characteristic.getDescriptor(CCC_UUID.toJavaUuid())

        val value = ENABLE_NOTIFICATION_VALUE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            check(
                gatt.writeDescriptor(
                    cccDescriptor,
                    value
                ) == BluetoothStatusCodes.SUCCESS
            )
        } else {
            cccDescriptor.value = value
            check(gatt.writeDescriptor(cccDescriptor))
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCharacteristicChanged(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic,
    ) {
        logger.v { "onCharacteristicChanged(${characteristic.uuid})" }
        this.gatt = gatt
        readBuffer.write(characteristic.value)
    }

    override fun onCharacteristicChanged(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic,
        value: ByteArray,
    ) {
        logger.v { "onCharacteristicChanged(${characteristic.uuid})" }
        this.gatt = gatt
        readBuffer.write(value)
    }

    override fun onCharacteristicWrite(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic,
        status: Int,
    ) {
        logger.v { "onCharacteristicWrite(${characteristic.uuid}, $status)" }
        if (status == GATT_SUCCESS) {
            writeCont?.resume(Unit)
        } else {
            writeCont?.resumeWithException(CommunicationFailedException("Write failed: status=$status"))
        }
        writeCont = null
    }
}
