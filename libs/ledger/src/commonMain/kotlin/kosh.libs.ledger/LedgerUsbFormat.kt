package kosh.libs.ledger

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
import kotlin.math.min
import kotlin.random.Random

class LedgerUsbFormat(
    private val connection: Transport.Connection,
) : Transport.Connection {

    private val logger = Logger.withTag("[K]LedgerConnection")

    private val channel = Random.nextInt()

    override val writeMtu: Int
        get() = connection.writeMtu

    override suspend fun write(data: ByteString) {
        logger.v { "write(${data.size})" }
        val source = Buffer().apply {
            writeShort(data.size.toShort())
            write(data)
        }

        val buffer = Buffer()
        var counter = 0
        while (source.exhausted().not()) {
            buffer.writeShort(channel.toShort())
            buffer.writeByte(TAG.toByte())
            buffer.writeShort(counter.toShort())
            buffer.write(source, min(writeMtu - 5L, source.size))
            connection.write(buffer.readByteString())
            counter++
        }
    }

    override suspend fun read(): ByteString {
        val buffer = Buffer()

        val msgSize = decodeHeader(source = readHeader(), sink = buffer)

        logger.v { "read($msgSize)" }

        var counter = 1

        while (msgSize > buffer.size) {
            val chunk = Buffer().apply { write(connection.read()) }

            require(chunk.readShort() == channel.toShort())
            require(chunk.readByte() == TAG.toByte())
            require(chunk.readShort() == counter.toShort())

            buffer.write(chunk, min(msgSize - buffer.size, chunk.size))

            counter++
        }

        return buffer.readByteString()
    }

    private suspend fun readHeader(): Buffer {
        logger.v { "readHeader()" }
        while (true) {
            currentCoroutineContext().ensureActive()

            val header = connection.read()
            val buffer = Buffer().apply { write(header) }
            if (isHeader(buffer)) {
                return buffer
            }
        }
    }

    private fun isHeader(
        source: Source,
    ): Boolean = source.peek().let { peek ->
        peek.exhausted().not() &&
                channel.toShort() == peek.readShort() &&
                peek.readByte() == TAG.toByte() &&
                peek.readShort() == 0.toShort()
    }

    private fun decodeHeader(
        source: Buffer,
        sink: Sink,
    ): Short {
        require(source.readShort() == channel.toShort())
        require(source.readByte() == TAG.toByte())
        require(source.readShort() == 0.toShort())

        val msgSize = source.readShort()

        sink.write(source, min(msgSize.toLong(), source.size))

        return msgSize
    }


    override fun close() {
        connection.close()
    }
}
