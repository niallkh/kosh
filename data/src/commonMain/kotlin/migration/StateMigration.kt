package kosh.data.migration

interface StateMigration<T> {
    suspend fun shouldMigrate(currentData: T): Boolean
    suspend fun migrate(currentData: T): T
    suspend fun cleanUp()
}
