package kosh.libs.usb

import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbEndpoint
import android.hardware.usb.UsbRequest
import co.touchlab.kermit.Logger
import kosh.libs.transport.Transport
import kosh.libs.transport.TransportException.CommunicationFailedException
import kosh.libs.transport.TransportException.ConnectionFailedException
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
        if (!initialize(connection, writeEndpoint))
            throw ConnectionFailedException("Initialization write usb request failed")
    }

    private val readRequest = UsbRequest().apply {
        if (!initialize(connection, readEndpoint)) {
            throw ConnectionFailedException("Initialization read usb request failed")
        }
    }

    override val writeMtu: Int
        get() = packetSize

    override suspend fun write(data: ByteString) = writeMutex.withLock {
        writeBuffer.clear()
        UnsafeByteStringOperations.withByteArrayUnsafe(data) {
            writeBuffer.put(it)
        }
        writeBuffer.flip()
        logger.v { "write(size = ${writeBuffer.limit()})" }
        writeRequest.transfer(writeBuffer)
        Unit
    }

    override suspend fun read(): ByteString = readMutex.withLock {
        readBuffer.clear()
        readRequest.transfer(readBuffer)
        readBuffer.flip()
        logger.v { "read(size = ${writeBuffer.limit()})" }
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
            if (!queue(buffer)) {
                continuation.resumeWithException(ConnectionFailedException("Queue buffer failed"))
            } else {
                clientData = { continuation.resume(buffer) }
                continuation.invokeOnCancellation { this@transfer.cancel() }
                val request = connection.requestWait()
                if (request == null) {
                    continuation.resumeWithException(CommunicationFailedException("Transfer buffer failed"))
                } else {
                    @Suppress("UNCHECKED_CAST")
                    (request.clientData as () -> Unit).invoke()
                }
            }
        }
    }
}
