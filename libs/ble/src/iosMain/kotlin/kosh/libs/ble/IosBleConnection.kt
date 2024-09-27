@file:OptIn(ExperimentalForeignApi::class)

package kosh.libs.ble

import co.touchlab.kermit.Logger
import kosh.libs.transport.Transport
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCSignatureOverride
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import kotlinx.io.readByteString
import platform.CoreBluetooth.CBCharacteristic
import platform.CoreBluetooth.CBPeripheral
import platform.CoreBluetooth.CBPeripheralDelegateProtocol
import platform.CoreBluetooth.CBService
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.darwin.NSObject
import platform.posix.memcpy
import kotlin.uuid.Uuid

class IosBleConnection(
    private val config: BleConfig,
    private val peripheral: CBPeripheral,
    private val dispatcher: CoroutineDispatcher,
) : Transport.Connection {

    private val logger = Logger.withTag("[K]IosBleConnection")

    private val writeCharacteristic = CompletableDeferred<CBCharacteristic>()

    private val readMutex = Mutex()
    private val writeMutex = Mutex()
    private val readBuffer = Buffer()

    override var mtu: Int = 23

    private val callback = object : CBPeripheralDelegateProtocol, NSObject() {
        override fun peripheral(peripheral: CBPeripheral, didDiscoverServices: NSError?) {
            logger.v { "didDiscoverServices: ${peripheral.identifier.UUIDString}" }
            peripheral.services?.forEach {
                peripheral.discoverCharacteristics(null, it as CBService)
            }
        }

        @ObjCSignatureOverride
        override fun peripheral(
            peripheral: CBPeripheral,
            didDiscoverCharacteristicsForService: CBService,
            error: NSError?,
        ) {
            logger.v { "didDiscoverCharacteristicsForService: ${didDiscoverCharacteristicsForService.UUID.UUIDString}" }

            didDiscoverCharacteristicsForService.characteristics?.asSequence()
                ?.map { it as CBCharacteristic }
                ?.find { ch -> Uuid.parse(ch.UUID.UUIDString) in config.charWriteUuid }
                ?.let { writeCharacteristic.complete(it) }

            didDiscoverCharacteristicsForService.characteristics?.asSequence()
                ?.map { it as CBCharacteristic }
                ?.find { ch -> Uuid.parse(ch.UUID.UUIDString) in config.charNotifyUuid }
                ?.let { peripheral.setNotifyValue(true, it) }
        }

        @ObjCSignatureOverride
        override fun peripheral(
            peripheral: CBPeripheral,
            didUpdateValueForCharacteristic: CBCharacteristic,
            error: NSError?,
        ) {
            logger.v { "didUpdateValueForCharacteristic: ${didUpdateValueForCharacteristic.UUID.UUIDString}" }

            didUpdateValueForCharacteristic.value?.let {
                readBuffer.write(it.toByteArray())
            }
        }
    }

    suspend fun initialize() {
        logger.v { "initialize" }
        peripheral.delegate = callback
        peripheral.discoverServices(config.serviceUuid.map { cbUuid(it) })
        mtu = peripheral.maximumWriteValueLengthForType(1).toInt()
        writeCharacteristic.await()
    }

    override suspend fun write(data: ByteString) = withContext(dispatcher) {
        writeMutex.withLock {
            logger.v { "write" }
            val characteristic = writeCharacteristic.await()

            UnsafeByteStringOperations.withByteArrayUnsafe(data) {
                peripheral.writeValue(it.toData(), characteristic, 1)
            }
        }
    }

    override suspend fun read(): ByteString = withContext(dispatcher) {
        readMutex.withLock {
            logger.v { "read" }
            while (readBuffer.size == 0L) {
                delay(10)
            }

            readBuffer.readByteString()
        }
    }

    override fun close() {
        logger.v { "close" }
    }
}

internal fun NSData.string(): String? {
    return NSString.create(this, NSUTF8StringEncoding) as String?
}

internal fun NSData.toByteArray(): ByteArray {
    val data = this
    val d = memScoped { data }
    return ByteArray(d.length.toInt()).apply {
        usePinned {
            memcpy(it.addressOf(0), d.bytes, d.length)
        }
    }
}

internal fun ByteArray.toData(): NSData = memScoped {
    NSData.create(
        bytes = allocArrayOf(this@toData),
        length = this@toData.size.toULong()
    )
}
