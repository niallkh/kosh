package kosh.app.di.impl

import kosh.app.di.AppComponent
import kosh.app.di.FilesComponent
import kosh.app.di.SerializationComponent
import kosh.datastore.AppStateDataStore
import kosh.datastore.DataStoreComponent
import okio.FileSystem
import okio.SYSTEM

internal class DefaultDataStoreComponent(
    filesComponent: FilesComponent,
    serializationComponent: SerializationComponent,
    appComponent: AppComponent,
) : DataStoreComponent,
    AppComponent by appComponent,
    FilesComponent by filesComponent,
    SerializationComponent by serializationComponent {

    override val appStateDataStore: AppStateDataStore = AppStateDataStore(
        path = appDataStorePath,
        fileSystem = FileSystem.SYSTEM,
        cbor = cbor,
        debug = debug
    )
}
