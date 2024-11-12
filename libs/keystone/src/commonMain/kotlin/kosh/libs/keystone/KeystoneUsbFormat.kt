package kosh.libs.keystone

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
import kotlin.math.ceil
import kotlin.math.min
import kotlin.random.Random

private const val HEADER = 9L

class KeystoneUsbFormat(
    private val connection: Transport.Connection,
) : Transport.Connection by connection {

    private val logger = Logger.withTag("[K]LedgerConnection")

    private var requestId: Short = 0

    override suspend fun write(data: ByteString) {
        logger.v { "write(${data.size})" }
        val (command, message) = parseWriteData(data)
        this.requestId = Random.nextInt().toShort()

        val source = Buffer().apply { write(message) }

        val total = ceil(message.size.toFloat() / (writeMtu - HEADER).toFloat())
        var counter = 0

        val buffer = Buffer()
        while (source.exhausted().not()) {
            buffer.writeByte(0)
            buffer.writeShort(command)
            buffer.writeShort(total.toInt().toShort())
            buffer.writeShort(counter.toShort())
            buffer.writeShort(requestId)
            buffer.write(source, min(writeMtu - HEADER, source.size))
            connection.write(buffer.readByteString())
            counter++
        }
    }

    private fun parseWriteData(data: ByteString): Pair<Short, ByteString> {
        val source = Buffer().apply { write(data) }
        val command = source.readShort()
        val message = source.readByteString()
        return Pair(command, message)
    }

    override suspend fun read(): ByteString {
        val buffer = Buffer()

        var (ins, total, statusCode) = decodePacket(source = readHeader(), sink = buffer)

        logger.v { "read($ins, $total, $statusCode)" }

        var counter = 1

        while (counter < total) {
            val chunk = Buffer().apply { write(connection.read()) }

            require(chunk.readByte() == 0.toByte())
            ins = chunk.readShort()
            total = chunk.readShort()
            require(chunk.readShort() == counter.toShort())
            require(chunk.readShort() == requestId)
            buffer.write(chunk, min(writeMtu - 11L, chunk.size - 2))
            statusCode = chunk.readShort()

            counter++
        }

        buffer.writeShort(statusCode)

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
                peek.readByte() == 0.toByte() &&
                run { peek.readShort(); true } &&
                run { peek.readShort(); true } &&
                peek.readShort() == 0.toShort() &&
                peek.readShort() == requestId
    }

    private fun decodePacket(
        source: Buffer,
        sink: Sink,
        index: Int = 0,
    ): Triple<Short, Short, Short> {
        require(source.readByte() == 0.toByte())
        val ins = source.readShort()
        val total = source.readShort()
        require(source.readShort() == index.toShort())
        require(source.readShort() == requestId)

        sink.write(source, min(writeMtu - HEADER - 2, source.size - 2))
        val statusCode = source.readShort()

        return Triple(ins, total, statusCode)
    }


    override fun close() {
        connection.close()
    }
}
