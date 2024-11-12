package kosh.libs.keystone

import co.touchlab.kermit.Logger
import kosh.libs.transport.Transport
import kosh.libs.transport.buildByteString
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.toHexString
import kotlinx.io.readByteString
import kotlinx.io.write

internal class DefaultKeystoneConnection(
    private val connection: Transport.Connection,
    private val listener: KeystoneManager.Connection.Listener,
) : KeystoneManager.Connection {

    private val logger = Logger.withTag("[K]KeystoneConnection")

    override suspend fun exchange(
        command: Actions,
        data: ByteString,
    ): Pair<StatusCode, ByteString> {
        logger.d { "-> $command" }
        logger.v { "data = ${data.toHexString()}" }

        write(command, data)

        logger.v { "---" }

        val (sc, response) = read()

        logger.d { "<- $sc" }

        return sc to response
    }

    private suspend fun write(
        command: Actions,
        data: ByteString,
    ) {
        connection.write(
            buildByteString {
                writeShort(command.value.toShort())
                write(data)
            }
        )
    }

    private suspend fun read(): Pair<StatusCode, ByteString> {
        val buffer = Buffer().apply { write(connection.read()) }

        val response = buffer.readByteString((buffer.size - 2).toInt())
        val statusCode = buffer.readShort()

        val sc = StatusCode.entries.find { it.code == statusCode.toLong() }
            ?: error("Unknown status code: $statusCode")

        return Pair(sc, response)
    }
}
