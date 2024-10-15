@file:OptIn(ExperimentalForeignApi::class)

package kosh.libs.ble

import co.touchlab.kermit.Logger
import kosh.libs.transport.Transport
import kosh.libs.transport.TransportException.CommunicationFailedException
import kosh.libs.transport.TransportException.ConnectionFailedException
import kosh.libs.transport.TransportException.DeviceDisconnectedException
import kosh.libs.transport.TransportException.ResponseTimeoutException
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCSignatureOverride
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import kotlinx.io.readByteString
import platform.CoreBluetooth.CBCharacteristic
import platform.CoreBluetooth.CBCharacteristicWriteWithResponse
import platform.CoreBluetooth.CBPeripheral
import platform.CoreBluetooth.CBPeripheralDelegateProtocol
import platform.CoreBluetooth.CBPeripheralStateConnected
import platform.CoreBluetooth.CBService
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.darwin.NSObject
import platform.posix.memcpy
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.math.min
import kotlin.time.Duration.Companion.seconds
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

    private var writeCont: Continuation<Unit>? = null

    override var writeMtu: Int = MTU_MIN

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
                ?.find { ch -> Uuid.parse(ch.UUID.UUIDString) in config.charWriteUuids }
                ?.let {
                    writeMtu = min(
                        peripheral
                            .maximumWriteValueLengthForType(CBCharacteristicWriteWithResponse)
                            .toInt(),
                        config.maxWriteMtu - MTU_HEADER
                    )
                    logger.v { "mtu=$writeMtu" }
                    writeCharacteristic.complete(it)
                }

            didDiscoverCharacteristicsForService.characteristics?.asSequence()
                ?.map { it as CBCharacteristic }
                ?.find { ch -> Uuid.parse(ch.UUID.UUIDString) in config.charNotifyUuids }
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

        @ObjCSignatureOverride
        override fun peripheral(
            peripheral: CBPeripheral,
            didWriteValueForCharacteristic: CBCharacteristic,
            error: NSError?,
        ) {
            logger.v { "didWriteValueForCharacteristic: ${didWriteValueForCharacteristic.UUID.UUIDString}" }
            if (error == null) {
                writeCont?.resume(Unit)
            } else {
                writeCont?.resumeWithException(CommunicationFailedException(error.localizedDescription))
            }
            writeCont = null
        }
    }

    suspend fun initialize() {
        logger.v { "initialize" }
        peripheral.delegate = callback
        withTimeoutOrNull(10.seconds) {
            peripheral.discoverServices(config.serviceUuids.map { cbUuid(it) })
            writeCharacteristic.await()
        } ?: throw ConnectionFailedException("Initialization failed")
    }

    override suspend fun write(data: ByteString) = withContext(dispatcher) {
        writeMutex.withLock {
            logger.v { "write(${data.size})" }
            if (peripheral.state != CBPeripheralStateConnected)
                throw DeviceDisconnectedException()

            val characteristic = writeCharacteristic.await()

            UnsafeByteStringOperations.withByteArrayUnsafe(data) {
                peripheral.writeValue(
                    data = it.toData(),
                    forCharacteristic = characteristic,
                    type = CBCharacteristicWriteWithResponse
                )
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
                if (peripheral.state != CBPeripheralStateConnected)
                    throw DeviceDisconnectedException()

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
