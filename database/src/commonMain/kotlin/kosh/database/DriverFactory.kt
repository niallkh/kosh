package kosh.database

import app.cash.sqldelight.db.SqlDriver

fun interface DriverFactory : suspend () -> SqlDriver

