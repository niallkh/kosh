package kosh.libs.usb

import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbEndpoint
import android.hardware.usb.UsbRequest
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import okio.BufferedSink
import okio.BufferedSource
import java.nio.ByteBuffer
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.math.ceil

class AndroidUsbConnection(
    private val connection: UsbDeviceConnection,
    private val writeEndpoint: UsbEndpoint,
    private val readEndpoint: UsbEndpoint,
    private val packetSize: Int,
) : Usb.Connection {

    private val logger = Logger.withTag("[K]Usb.Connection")

    private val readBuffer = ByteBuffer.allocateDirect(16384)
    private val writeBuffer = ByteBuffer.allocateDirect(16384)

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

    override suspend fun write(
        source: BufferedSource,
    ): Unit = writeMutex.withLock {
        writeBuffer.clear()
        source.read(writeBuffer)
        writeBuffer.flip()
        writeBuffer.limit(ceil((writeBuffer.limit() / packetSize.toFloat())).toInt() * packetSize)
        logger.v { "write(size = ${writeBuffer.limit()})" }
        writeRequest.transfer(writeBuffer)
    }

    override suspend fun read(
        sink: BufferedSink,
        length: Int,
    ): Unit = readMutex.withLock {
        readBuffer.clear()
        readBuffer.limit(ceil((length / packetSize.toFloat())).toInt() * packetSize)
        logger.v { "read(size = ${writeBuffer.limit()})" }
        readRequest.transfer(readBuffer)
        readBuffer.rewind()
        readBuffer.limit(length)
        sink.write(readBuffer)
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
