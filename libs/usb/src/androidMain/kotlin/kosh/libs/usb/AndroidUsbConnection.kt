package kosh.libs.usb

import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbEndpoint
import android.hardware.usb.UsbRequest
import co.touchlab.kermit.Logger
import kosh.libs.transport.Transport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import kotlinx.io.readByteString
import kotlinx.io.write
import java.nio.ByteBuffer
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AndroidUsbConnection(
    private val connection: UsbDeviceConnection,
    private val writeEndpoint: UsbEndpoint,
    private val readEndpoint: UsbEndpoint,
    private val packetSize: Int,
) : Transport.Connection {

    private val logger = Logger.withTag("[K]Usb.Connection")

    private val readBuffer = ByteBuffer.allocateDirect(packetSize)
    private val writeBuffer = ByteBuffer.allocateDirect(packetSize)

    private val readMutex = Mutex()
    private val writeMutex = Mutex()

    private val writeRequest = UsbRequest().apply {
        check(initialize(connection, writeEndpoint)) {
            "error happened during initialization write usb request"
        }
    }

    private val readRequest = UsbRequest().apply {
        check(initialize(connection, readEndpoint)) {
            "error happened during initialization read usb request"
        }
    }

    override val mtu: Int
        get() = packetSize

    override suspend fun write(data: ByteString) = writeMutex.withLock {
        writeBuffer.clear()
        UnsafeByteStringOperations.withByteArrayUnsafe(data) {
            writeBuffer.put(it)
        }
        writeBuffer.flip()
        writeBuffer.limit(mtu)
        logger.v { "write(size = ${writeBuffer.limit()})" }
        writeRequest.transfer(writeBuffer)
        Unit
    }

    override suspend fun read(): ByteString = readMutex.withLock {
        readBuffer.clear()
        readBuffer.limit(mtu)
        logger.v { "read(size = ${writeBuffer.limit()})" }
        readRequest.transfer(readBuffer)
        readBuffer.rewind()
        readBuffer.limit(mtu)
        Buffer().apply { write(readBuffer) }.readByteString()
    }

    override fun close() {
        logger.i { "close()" }
        try {
            writeRequest.close()
        } finally {
            readRequest.close()
        }
    }

    private suspend fun UsbRequest.transfer(
        buffer: ByteBuffer,
    ): ByteBuffer = withContext(Dispatchers.IO) {
        suspendCancellableCoroutine { continuation ->
            if (queue(buffer)) {
                clientData = { continuation.resume(buffer) }
                continuation.invokeOnCancellation { this@transfer.cancel() }
                val request = connection.requestWait()
                if (request != null) {
                    @Suppress("UNCHECKED_CAST")
                    (request.clientData as () -> Unit).invoke()
                } else {
                    continuation.resumeWithException(Exception("Transfer buffer failed"))
                }
            } else {
                continuation.resumeWithException(Exception("Queue buffer failed"))
            }
        }
    }
}
