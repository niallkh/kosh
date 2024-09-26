package kosh.libs.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGattCharacteristic
import co.touchlab.kermit.Logger
import kosh.libs.transport.Transport
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import kotlinx.io.readByteString
import kotlin.uuid.Uuid
import kotlin.uuid.toKotlinUuid

class AndroidBleConnection(
    private val config: BleConfig,
    private val adapter: BluetoothGattAdapter,
) : Transport.Connection {

    private val logger = Logger.withTag("[K]AndroidBleConnection")

    private val readMutex = Mutex()
    private val writeMutex = Mutex()

    override var mtu: Int = 23

    @SuppressLint("MissingPermission")
    suspend fun init() {
        logger.d { "init()" }
        adapter.connected.first { it }
        adapter.discoverServices()
        mtu = adapter.requestMtu(517) - 10
        val notifyCharacteristic = getCharacteristic(config.serviceUuid, config.notifyUuid)
            ?: error("Notifiable characteristic not found")
        adapter.enableNotification(notifyCharacteristic)
    }

    override suspend fun write(data: ByteString) = writeMutex.withLock {
        val writeCharacteristic = getCharacteristic(config.serviceUuid, config.writeUuid)
            ?: error("Writeable characteristic not found")

        UnsafeByteStringOperations.withByteArrayUnsafe(data) {
            adapter.writeCharacteristic(writeCharacteristic, it)
        }
    }

    override suspend fun read(): ByteString = readMutex.withLock {
        while (adapter.readBuffer.size == 0L) {
            delay(10)
        }

        Buffer().apply {
            if (adapter.readBuffer.request(mtu.toLong())) {
                write(adapter.readBuffer, mtu.toLong())
            } else {
                transferFrom(adapter.readBuffer)
            }
        }.readByteString()
    }

    override fun close() {

    }

    private fun getCharacteristic(
        services: List<Uuid>,
        ids: List<Uuid>,
    ): BluetoothGattCharacteristic? = adapter.gatt.services.asSequence()
        .filter { it.uuid.toKotlinUuid() in services }
        .flatMap { it.characteristics }
        .firstOrNull { it.uuid.toKotlinUuid() in ids }
}
