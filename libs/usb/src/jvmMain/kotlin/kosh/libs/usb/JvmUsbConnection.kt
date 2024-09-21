package kosh.libs.usb

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.io.Sink
import kotlinx.io.Source
import javax.usb.UsbInterface
import javax.usb.UsbIrp
import javax.usb.UsbPipe
import javax.usb.event.UsbPipeDataEvent
import javax.usb.event.UsbPipeErrorEvent
import javax.usb.event.UsbPipeListener
import javax.usb.util.DefaultUsbIrp
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.math.ceil

class JvmUsbConnection(
    private val usbInterface: UsbInterface,
    private val writePipe: UsbPipe,
    private val readPipe: UsbPipe,
) : Usb.Connection {

    private val writeBuffer = ByteArray(16384)
    private val readBuffer = ByteArray(16384)

    private val writeMutex = Mutex()
    private val readMutex = Mutex()

    init {
        readPipe.open()
        writePipe.open()
    }

    override suspend fun write(
        source: Source,
    ): Unit = writeMutex.withLock {
        val length = source.readAtMostTo(writeBuffer)
        val usbIrp = DefaultUsbIrp(writeBuffer, 0, ceil((length / 64.0)).toInt() * 64, false)
        writePipe.transferAsync(usbIrp)
    }

    override suspend fun read(
        sink: Sink,
        length: Int,
    ): Unit = readMutex.withLock {
        val usbIrp = DefaultUsbIrp(readBuffer, 0, ceil((length / 64.0)).toInt() * 64, false)
        readPipe.transferAsync(usbIrp)
        sink.write(readBuffer, 0, usbIrp.actualLength)
    }

    override fun close() {
        try {
            readPipe.close()
        } finally {
            writePipe.close()
        }
        usbInterface.release()
    }
}

private suspend fun UsbPipe.transferAsync(
    usbIrp: UsbIrp,
): UsbIrp = withContext(Dispatchers.IO) {
    suspendCancellableCoroutine { continuation ->
        val listener = object : UsbPipeListener {
            override fun errorEventOccurred(event: UsbPipeErrorEvent) {
                if (event.usbIrp === usbIrp) {
                    removeUsbPipeListener(this)
                    continuation.resumeWithException(event.usbException)
                }
            }

            override fun dataEventOccurred(event: UsbPipeDataEvent) {
                if (event.usbIrp === usbIrp) {
                    removeUsbPipeListener(this)
                    continuation.resume(usbIrp)
                }
            }
        }

        continuation.invokeOnCancellation {
            removeUsbPipeListener(listener)
            abortAllSubmissions()
        }
        addUsbPipeListener(listener)
        asyncSubmit(usbIrp)
    }
}
