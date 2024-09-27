package kosh.libs.transport

import arrow.fx.coroutines.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.io.bytestring.ByteString

interface Transport<Config> {

    fun devices(
        config: Config,
    ): Flow<List<Device>> // TODO: do scan limited period of time

    suspend fun open(
        id: String,
        config: Config,
    ): Resource<Connection>

    interface Connection : AutoCloseable {

        val mtu: Int

        suspend fun write(data: ByteString)

        suspend fun read(): ByteString
    }
}
