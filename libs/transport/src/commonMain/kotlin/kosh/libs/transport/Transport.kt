package kosh.libs.transport

import arrow.fx.coroutines.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString

interface Transport<Config> {

    fun register(config: Config)

    fun devices(config: Config): Flow<List<Device>>

    suspend fun open(
        id: String,
        config: Config,
    ): Resource<Connection>

    interface Connection : AutoCloseable {

        val writeMtu: Int

        suspend fun write(data: ByteString)

        suspend fun read(): ByteString
    }
}

inline fun buildByteString(
    block: Sink.() -> Unit,
) = Buffer().apply {
    block()
}.readByteString()
