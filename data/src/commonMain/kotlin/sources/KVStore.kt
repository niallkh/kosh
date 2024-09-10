package kosh.data.sources

import kotlinx.coroutines.flow.Flow
import okio.ByteString

interface KVStore {
    fun get(key: ByteString): Flow<ByteString?>

    suspend fun update(key: ByteString, update: (ByteString?) -> ByteString?): ByteString?
}
