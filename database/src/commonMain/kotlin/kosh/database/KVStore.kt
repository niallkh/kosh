package kosh.database

import app.cash.sqldelight.coroutines.asFlow
import kosh.data.sources.KVStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okio.ByteString

class DefaultKVStore(
    private val queries: KVQueries,
) : KVStore {

    override fun get(key: ByteString): Flow<ByteString?> = flow {
        emitAll(queries.get(key).asFlow().map { it.executeAsOneOrNull() })
    }.flowOn(Dispatchers.Default)

    override suspend fun update(
        key: ByteString,
        update: (ByteString?) -> ByteString?,
    ): ByteString? = withContext(Dispatchers.Default) {
        queries.transactionWithResult {
            val previous = queries.get(key).executeAsOneOrNull()

            val updated = update(previous)

            if (updated != null) {
                queries.insert(key, updated)
            } else {
                queries.delete(key)
            }

            updated
        }
    }
}
