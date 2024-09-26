package kosh.libs.trezor

import co.touchlab.kermit.Logger
import kosh.libs.transport.Transport
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.Source
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString
import kotlinx.io.write
import kotlinx.io.writeString
import kotlin.math.min

class TrezorUsbFormat(
    private val connection: Transport.Connection,
) : Transport.Connection {

    private val logger = Logger.withTag("[K]TrezorConnection")

    override val mtu: Int
        get() = connection.mtu

    override suspend fun write(data: ByteString) {
        logger.v { "write(${data.size})" }
        val (message, msgType) = parseWriteData(data)
        val source = Buffer().apply { write(message) }

        val buffer = Buffer()
        buffer.writeString("?##")
        buffer.writeShort(msgType)
        buffer.writeInt(message.size)
        buffer.write(source, min(mtu - 9L, source.size))
        connection.write(buffer.readByteString())

        while (source.exhausted().not()) {
            buffer.writeString("?")
            buffer.write(source, min(mtu - 1L, source.size))
            connection.write(buffer.readByteString())
        }
    }

    private fun parseWriteData(data: ByteString): Pair<ByteString, Short> {
        val source = Buffer().apply { write(data) }
        val message = source.readByteString(data.size - 2)
        val msgType = source.readShort()
        return message to msgType
    }

    override suspend fun read(): ByteString {
        val buffer = Buffer()

        val (msgType, msgSize) = decodeHeader(source = readHeader(), sink = buffer)

        logger.v { "read($msgSize)" }

        while (msgSize > buffer.size) {
            val chunk = Buffer().apply { write(connection.read()) }

            require(chunk.readByte() == '?'.code.toByte())

            buffer.write(chunk, min(msgSize - buffer.size, mtu - 1L))
        }

        return buffer.run {
            writeShort(msgType)
            readByteString()
        }
    }


    private suspend fun readHeader(): Buffer {
        logger.v { "readHeader()" }
        while (true) {
            currentCoroutineContext().ensureActive()

            val buffer = Buffer().apply { write(connection.read()) }

            if (isHeader(buffer)) {
                return buffer
            }
        }
    }

    private fun isHeader(
        source: Source,
    ): Boolean = source.peek().let {
        it.exhausted().not() &&
                it.readByte() == '?'.code.toByte() &&
                it.exhausted().not() &&
                it.readByte() == '#'.code.toByte() &&
                it.exhausted().not() &&
                it.readByte() == '#'.code.toByte()
    }

    private fun decodeHeader(
        source: Source,
        sink: Sink,
    ): Pair<Short, Int> {
        require(source.readByte() == '?'.code.toByte())
        require(source.readByte() == '#'.code.toByte())
        require(source.readByte() == '#'.code.toByte())
        val msgType = source.readShort()
        val msgSize = source.readInt()

        if (msgSize <= mtu - 9) {
            sink.write(source, msgSize.toLong())
        } else {
            sink.transferFrom(source)
        }

        return msgType to msgSize
    }

    override fun close() {
        connection.close()
    }
}
