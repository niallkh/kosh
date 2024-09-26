package kosh.libs.ledger

import co.touchlab.kermit.Logger
import kosh.libs.ledger.LedgerManager.Listener.ButtonRequest
import kosh.libs.transport.Transport
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.toHexString
import kotlinx.io.readByteString
import kotlinx.io.write

class DefaultLedgerConnection(
    private val connection: Transport.Connection,
    private val listener: LedgerManager.Listener,
) : LedgerManager.Connection {

    private val logger = Logger.withTag("[K]LedgerConnection")

    override suspend fun exchange(
        ledgerAPDU: LedgerAPDU,
    ): Pair<StatusWord, ByteString> {
        logger.d {
            with(ledgerAPDU) {
                "-> 0x${cla.toUByte().toString(16)} 0x${ins.toUByte().toString(16)} " +
                        "0x${p1.toUByte().toString(16)} 0x${p2.toUByte().toString(16)}"
            }
        }
        logger.v {
            "data = ${ledgerAPDU.data.toHexString()}"
        }
        with(ledgerAPDU) {
            write(cla, ins, p1, p2, data)
        }
        logger.v { "---" }
        val (statusWord, bytes) = read()
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
        cla: Byte,
        ins: Byte,
        p1: Byte,
        p2: Byte,
        data: ByteString,
    ) {
        require(data.size < 256)

        val message = Buffer().apply {
            writeByte(cla)
            writeByte(ins)
            writeByte(p1)
            writeByte(p2)
            writeByte(data.size.toByte())
            write(data)
        }.readByteString()

        connection.write(message)
    }

    private suspend fun read(): Pair<StatusWord, ByteString> {
        val buffer = Buffer().apply { write(connection.read()) }

        val bytes = buffer.readByteString((buffer.size - 2).toInt())
        val code = buffer.readShort()
        val statusWord = StatusWord.entries.find { it.code.toShort() == code }

        if (statusWord == null) {
            error("Unexpected status word from ledger: 0x${code.toString(16)}")
        }

        return statusWord to bytes
    }
}
