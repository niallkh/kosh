package kosh.libs.transport

import arrow.fx.coroutines.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.io.bytestring.ByteString

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
