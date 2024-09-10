package kosh.datastore

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.core.okio.OkioStorage
import kosh.datastore.migrations.migration1
import kosh.domain.state.AppState
import kotlinx.serialization.cbor.Cbor
import okio.FileSystem
import okio.Path

class AppStateDataStore(
    path: () -> Path,
    fileSystem: FileSystem,
    cbor: Cbor,
    debug: Boolean,
) : DataStore<AppState> by DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = fileSystem,
        producePath = path,
        serializer = AppStateSerializer(cbor),
    ),
    migrations = listOf(
        migration1(debug),
    ),
    corruptionHandler = corruptionHandler()
)

expect fun corruptionHandler(): ReplaceFileCorruptionHandler<AppState>

internal fun appStateMigration(
    version: Int,
    migration: AppState.() -> AppState,
) = object : DataMigration<AppState> {
    override suspend fun cleanUp() {
    }

    override suspend fun shouldMigrate(currentData: AppState): Boolean {
        return currentData.version < version
    }

    override suspend fun migrate(currentData: AppState): AppState {
        return currentData.migration().copy(version = version)
    }
}
