package kosh.database

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun androidDriverFactory(
    context: Context,
    name: String,
): DriverFactory = DriverFactory {
    withContext(Dispatchers.Default) {
        AndroidSqliteDriver(
            schema = Database.Schema,
            context = context,
            name = "$name.db",
            callback = object : AndroidSqliteDriver.Callback(Database.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.enableWriteAheadLogging()
                }
            }
        )
    }
}
