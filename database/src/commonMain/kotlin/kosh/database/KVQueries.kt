package kosh.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import okio.ByteString

class KVQueries(
    driver: SqlDriver,
    private val kvAdapter: Kv.Adapter,
) : TransacterImpl(driver) {

    fun get(key: ByteString): Query<ByteString> = GetQuery(key) { cursor ->
        kvAdapter.value_Adapter.decode(cursor.getBytes(0)!!)
    }

    fun insert(key: ByteString, `value`: ByteString) {
        driver.execute(
            2_144_218_317, """
        |INSERT OR REPLACE INTO kv(key, value)
        |VALUES (?, ?)
        """.trimMargin(), 2
        ) {
            bindBytes(0, kvAdapter.keyAdapter.encode(key))
            bindBytes(1, kvAdapter.value_Adapter.encode(value))
        }
        notifyQueries(2_144_218_317) { emit ->
            emit(key.hex())
        }
    }

    fun delete(key: ByteString) {
        driver.execute(
            1_992_552_383, """
        |DELETE FROM kv
        |WHERE key = ?
        """.trimMargin(), 1
        ) {
            bindBytes(0, kvAdapter.keyAdapter.encode(key))
        }
        notifyQueries(1_992_552_383) { emit ->
            emit(key.hex())
        }
    }

    private inner class GetQuery<out T : Any>(
        val key: ByteString,
        mapper: (SqlCursor) -> T,
    ) : Query<T>(mapper) {
        override fun addListener(listener: Listener) {
            driver.addListener(key.hex(), listener = listener)
        }

        override fun removeListener(listener: Listener) {
            driver.removeListener(key.hex(), listener = listener)
        }

        override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
            driver.executeQuery(
                1_199_996_354, """
    |SELECT value FROM kv
    |WHERE key=?
    |LIMIT 1
    """.trimMargin(), mapper, 1
            ) {
                bindBytes(0, kvAdapter.keyAdapter.encode(key))
            }

        override fun toString(): String = "kv.sq:get"
    }
}
