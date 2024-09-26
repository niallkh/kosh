package kosh.libs.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
import android.bluetooth.BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
import android.bluetooth.BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
import android.bluetooth.BluetoothProfile
import android.bluetooth.BluetoothStatusCodes
import android.os.Build
import arrow.atomic.value
import arrow.core.continuations.AtomicRef
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.io.Buffer
import java.util.UUID
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val CCC_UUID = "00002902-0000-1000-8000-00805f9b34fb"

class BluetoothGattAdapter : BluetoothGattCallback() {

    private val logger = Logger.withTag("[K]BluetoothGattAdapter")
    private val mutex = Mutex()
    private var continuation = AtomicRef<Continuation<Any>>()

    lateinit var gatt: BluetoothGatt
    val connected = MutableStateFlow(false)
    val readBuffer = Buffer()

    override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
        logger.v { "onConnectionStateChange($status, $newState)" }
        this.gatt = gatt
        when (newState) {
            BluetoothProfile.STATE_CONNECTED -> connected.value = true
            BluetoothProfile.STATE_DISCONNECTED,
            BluetoothProfile.STATE_DISCONNECTING,
            BluetoothProfile.STATE_CONNECTING,
            -> connected.value = false
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun requestMtu(mtu: Int): Int = mutex.withLock {
        logger.v { "requestMtu($mtu)" }
        check(gatt.requestMtu(mtu))
        suspendCoroutine { continuation.value = it } as Int
    }

    override fun onMtuChanged(gatt: BluetoothGatt, mtu: Int, status: Int) {
        logger.v { "onMtuChanged($mtu, $status)" }
        this.gatt = gatt
        if (status == BluetoothStatusCodes.SUCCESS) {
            continuation.getAndUpdate { null }?.resume(mtu)
        } else {
            continuation.getAndUpdate { null }
                ?.resumeWithException(IllegalStateException("Mtu change failed"))
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun discoverServices() = mutex.withLock {
        logger.v { "discoverServices()" }
        check(gatt.discoverServices())
        suspendCoroutine { continuation.value = it }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        logger.v { "onServicesDiscovered($status)" }
        this.gatt = gatt
        if (status == BluetoothStatusCodes.SUCCESS) {
            continuation.getAndUpdate { null }?.resume(Unit)
        } else {
            continuation.getAndUpdate { null }
                ?.resumeWithException(IllegalStateException("Service discovery failed"))
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun enableNotification(
        characteristic: BluetoothGattCharacteristic,
    ): Unit = mutex.withLock {
        logger.v { "enableNotification(${characteristic.uuid})" }
        require(characteristic.isNotifiable()) { "Characteristic is not notifiable" }

        check(gatt.setCharacteristicNotification(characteristic, true))

        val cccDescriptor = characteristic.getDescriptor(UUID.fromString(CCC_UUID))

        val value = ENABLE_NOTIFICATION_VALUE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            check(gatt.writeDescriptor(cccDescriptor, value) == BluetoothStatusCodes.SUCCESS)
        } else {
            cccDescriptor.value = value
            check(gatt.writeDescriptor(cccDescriptor))
        }
        suspendCoroutine { continuation.value = it }
        Unit
    }

    @SuppressLint("MissingPermission")
    suspend fun disableNotification(
        characteristic: BluetoothGattCharacteristic,
    ): Unit = mutex.withLock {
        logger.v { "disableNotification(${characteristic.uuid})" }
        require(characteristic.isNotifiable()) { "Characteristic is not notifiable" }

        check(gatt.setCharacteristicNotification(characteristic, false))

        val cccDescriptor = characteristic.getDescriptor(UUID.fromString(CCC_UUID))

        val value = DISABLE_NOTIFICATION_VALUE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            check(gatt.writeDescriptor(cccDescriptor, value) == BluetoothStatusCodes.SUCCESS)
        } else {
            cccDescriptor.value = value
            check(gatt.writeDescriptor(cccDescriptor))
        }
        suspendCoroutine { continuation.value = it }
        Unit
    }

    @SuppressLint("MissingPermission")
    suspend fun writeDescriptor(
        descriptor: BluetoothGattDescriptor,
        value: ByteArray,
    ) = mutex.withLock {
        logger.v { "writeDescriptor(${descriptor.uuid}, ${value.size})" }
        require(descriptor.isWritable()) { "Descriptor is not writable" }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            check(gatt.writeDescriptor(descriptor, value) == BluetoothStatusCodes.SUCCESS)
        } else {
            descriptor.value = value
            check(gatt.writeDescriptor(descriptor))
        }
        suspendCoroutine { continuation.value = it }
    }

    override fun onDescriptorWrite(
        gatt: BluetoothGatt,
        descriptor: BluetoothGattDescriptor,
        status: Int,
    ) {
        logger.v { "onDescriptorWrite(${descriptor.uuid}, $status, ${descriptor.value.toHexString()})" }
        this.gatt = gatt
        if (status == BluetoothStatusCodes.SUCCESS) {
            continuation.getAndUpdate { null }?.resume(Unit)
        } else {
            continuation.getAndUpdate { null }
                ?.resumeWithException(IllegalStateException("Descriptor write failed"))
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun writeCharacteristic(
        characteristic: BluetoothGattCharacteristic,
        value: ByteArray,
    ): Unit = mutex.withLock {
        logger.v { "writeCharacteristic(${characteristic.uuid}, ${value.size}, ${value.toHexString()})" }
        require(characteristic.isWritable()) { "Characteristic is not writable" }

        val writeType = if (characteristic.isWritableWithoutResponse()) WRITE_TYPE_NO_RESPONSE
        else WRITE_TYPE_DEFAULT

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            check(
                gatt.writeCharacteristic(
                    characteristic, value, writeType
                ) == BluetoothStatusCodes.SUCCESS
            )
        } else {
            characteristic.writeType = writeType
            characteristic.value = value
            check(gatt.writeCharacteristic(characteristic))
        }

        suspendCoroutine { continuation.value = it }
        Unit
    }

    override fun onCharacteristicWrite(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic,
        status: Int,
    ) {
        logger.v { "onCharacteristicWrite(${characteristic.uuid}, $status)" }
        this.gatt = gatt
        if (status == BluetoothStatusCodes.SUCCESS) {
            continuation.getAndUpdate { null }?.resume(Unit)
        } else {
            continuation.getAndUpdate { null }
                ?.resumeWithException(IllegalStateException("Characteristic write failed"))
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun readCharacteristic(
        characteristic: BluetoothGattCharacteristic,
    ): ByteArray = mutex.withLock {
        logger.v { "readCharacteristic(${characteristic.uuid})" }

        check(gatt.readCharacteristic(characteristic))

        suspendCoroutine { continuation.value = it } as ByteArray
    }

    @Deprecated("Deprecated in Java")
    override fun onCharacteristicRead(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic,
        status: Int,
    ) {
        logger.v { "onCharacteristicRead(${characteristic.uuid}, $status)" }
        this.gatt = gatt
        if (status == BluetoothStatusCodes.SUCCESS) {
            continuation.getAndUpdate { null }?.resume(characteristic.value)
        } else {
            continuation.getAndUpdate { null }
                ?.resumeWithException(IllegalStateException("Characteristic read failed"))
        }
    }

    override fun onCharacteristicRead(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic,
        value: ByteArray,
        status: Int,
    ) {
        logger.v { "onCharacteristicRead(${characteristic.uuid}, $status)" }
        this.gatt = gatt
        if (status == BluetoothStatusCodes.SUCCESS) {
            continuation.getAndUpdate { null }?.resume(value)
        } else {
            continuation.getAndUpdate { null }
                ?.resumeWithException(IllegalStateException("Characteristic read failed"))
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun readDescriptor(
        descriptor: BluetoothGattDescriptor,
    ): ByteArray = mutex.withLock {
        logger.v { "readDescriptor(${descriptor.uuid})" }
        check(descriptor.isReadable())

        gatt.readDescriptor(descriptor)

        suspendCoroutine { continuation.value = it } as ByteArray
    }

    @Deprecated("Deprecated in Java")
    override fun onDescriptorRead(
        gatt: BluetoothGatt,
        descriptor: BluetoothGattDescriptor,
        status: Int,
    ) {
        logger.v { "onDescriptorRead(${descriptor.uuid}, $status, ${descriptor.value.toHexString()})" }
        this.gatt = gatt
        if (status == BluetoothStatusCodes.SUCCESS) {
            continuation.getAndUpdate { null }?.resume(descriptor.value)
        } else {
            continuation.getAndUpdate { null }
                ?.resumeWithException(IllegalStateException("Descriptor read failed"))
        }
    }

    override fun onDescriptorRead(
        gatt: BluetoothGatt,
        descriptor: BluetoothGattDescriptor,
        status: Int,
        value: ByteArray,
    ) {
        logger.v { "onDescriptorRead(${descriptor.uuid}, $status, ${value.toHexString()})" }
        this.gatt = gatt
        if (status == BluetoothStatusCodes.SUCCESS) {
            continuation.getAndUpdate { null }?.resume(value)
        } else {
            continuation.getAndUpdate { null }
                ?.resumeWithException(IllegalStateException("Descriptor read failed"))
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun readRemoteRssi(): Unit = mutex.withLock {
        logger.v { "readRemoteRssi()" }

        check(gatt.readRemoteRssi())

        suspendCoroutine { continuation.value = it }
        Unit
    }

    override fun onReadRemoteRssi(gatt: BluetoothGatt, rssi: Int, status: Int) {
        logger.v { "onReadRemoteRssi($rssi, $status)" }
        this.gatt = gatt
        if (status == BluetoothStatusCodes.SUCCESS) {
            continuation.getAndUpdate { null }?.resume(Unit)
        } else {
            continuation.getAndUpdate { null }
                ?.resumeWithException(IllegalStateException("Descriptor read failed"))
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

    override fun onPhyUpdate(gatt: BluetoothGatt, txPhy: Int, rxPhy: Int, status: Int) {
        logger.v { "onPhyUpdate($status)" }
        this.gatt = gatt
    }

    override fun onPhyRead(gatt: BluetoothGatt, txPhy: Int, rxPhy: Int, status: Int) {
        logger.v { "onPhyRead($status)" }
        this.gatt = gatt
    }

    override fun onReliableWriteCompleted(gatt: BluetoothGatt, status: Int) {
        logger.v { "onReliableWriteCompleted($status)" }
        this.gatt = gatt
    }

    override fun onServiceChanged(gatt: BluetoothGatt) {
        logger.v { "onServiceChanged()" }
        this.gatt = gatt
    }

    private fun BluetoothGattCharacteristic.isWritable(): Boolean =
        containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE) ||
                containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)

    private fun BluetoothGattCharacteristic.isReadable(): Boolean =
        containsProperty(BluetoothGattCharacteristic.PROPERTY_READ)

    private fun BluetoothGattCharacteristic.isNotifiable(): Boolean =
        containsProperty(BluetoothGattCharacteristic.PROPERTY_NOTIFY)

    private fun BluetoothGattCharacteristic.isWritableWithoutResponse(): Boolean =
        containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)

    private fun BluetoothGattCharacteristic.containsProperty(property: Int): Boolean =
        properties and property != 0

    private fun BluetoothGattDescriptor.isWritable(): Boolean =
        containsPermission(BluetoothGattDescriptor.PERMISSION_WRITE)

    private fun BluetoothGattDescriptor.isReadable(): Boolean =
        containsPermission(BluetoothGattDescriptor.PERMISSION_READ)

    private fun BluetoothGattDescriptor.containsPermission(permission: Int): Boolean =
        permissions and permission != 0
}
