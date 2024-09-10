package kosh.libs.ledger

import co.touchlab.kermit.Logger
import kosh.libs.ledger.LedgerManager.Listener.ButtonRequest
import kosh.libs.usb.Usb
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import okio.Buffer
import okio.ByteString
import kotlin.math.ceil
import kotlin.random.Random

class DefaultLedgerConnection(
    private val usbConnection: Usb.Connection,
    private val listener: LedgerManager.Listener,
) : LedgerManager.Connection {
    private val logger = Logger.withTag("[K]LedgerConnection")

    override suspend fun exchange(
        ledgerAPDU: LedgerAPDU,
    ): Pair<StatusWord, ByteString> {
        val channel = Random.nextInt()
        logger.d {
            with(ledgerAPDU) {
                "-> 0x${cla.toString(16)} 0x${ins.toString(16)} " +
                        "0x${p1.toString(16)} 0x${p2.toString(16)}"
            }
        }
        logger.v {
            "data = ${ledgerAPDU.data.hex()}"
        }
        with(ledgerAPDU) {
            write(cla, ins, p1, p2, data, channel)
        }
        logger.v { "---" }
        val (statusWord, bytes) = read(channel)
        logger.d { "<- $statusWord" }

        return when (statusWord) {
            StatusWord.LOCKED_DEVICE -> {
                listener.buttonRequest(ButtonRequest.UnlockDevice)
                exchange(ledgerAPDU)
            }

            else -> statusWord to bytes
        }
    }

    private suspend fun write(
        cla: Int,
        ins: Int,
        p1: Int,
        p2: Int,
        data: ByteString,
        channel: Int,
    ) {
        require(data.size < 256)

        val message = Buffer().apply {
            writeByte(cla)
            writeByte(ins)
            writeByte(p1)
            writeByte(p2)
            writeByte(data.size)
            write(data)
        }.readByteString()

        usbConnection.write(Buffer().apply { write(encodeBytes(message, channel)) })
    }

    private suspend fun read(
        channel: Int,
    ): Pair<StatusWord, ByteString> {
        val buffer = Buffer()

        val msgSize = decodeHeader(source = readHeader(channel), sink = buffer, channel = channel)

        if (msgSize > buffer.size) {
            val left = msgSize - buffer.size.toInt()
            val checks = ceil(left / 59.0).toInt() * 5
            decodeData(
                source = Buffer().apply { usbConnection.read(this, left + checks) },
                sink = buffer,
                channel = channel,
            )
        }

        val bytes = buffer.readByteString(buffer.size - 2)
        val code = buffer.readShort()
        val statusWord = StatusWord.entries.find { it.code.toShort() == code }

        if (statusWord == null) {
            error("Unexpected status word from ledger: 0x${code.toString(16)}")
        }

        return statusWord to bytes
    }

    private suspend fun readHeader(
        channel: Int,
    ): Buffer {
        logger.v { "readHeader()" }
        val buffer = Buffer()
        while (true) {
            currentCoroutineContext().ensureActive()

            buffer.clear()

            usbConnection.read(buffer, PACKET_SIZE)

            if (isHeader(buffer, channel)) {
                return buffer
            }
        }
    }
}
