package kosh.database

import app.cash.sqldelight.ColumnAdapter
import kosh.domain.repositories.suspendLazy
import okio.ByteString
import okio.ByteString.Companion.toByteString

fun interface DatabaseProvider : suspend () -> Database

fun databaseProvider(driverFactory: DriverFactory) = DatabaseProvider(suspendLazy {
    Database(
        driver = driverFactory(),
        kvAdapter = Kv.Adapter(
            keyAdapter = ByteStringAdapter,
            value_Adapter = ByteStringAdapter,
        ),
    )
})

internal object ByteStringAdapter : ColumnAdapter<ByteString, ByteArray> {
    override fun decode(databaseValue: ByteArray): ByteString = databaseValue.toByteString()

    override fun encode(value: ByteString): ByteArray = value.toByteArray()
}
