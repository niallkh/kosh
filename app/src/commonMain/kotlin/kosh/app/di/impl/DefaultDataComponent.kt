package kosh.app.di.impl

import kosh.app.di.FilesComponent
import kosh.app.di.SerializationComponent
import kosh.data.DataComponent
import kosh.data.sources.KeyValueStore
import kosh.domain.core.provider
import kosh.files.FilesKeyValueStore

internal abstract class DefaultDataComponent(
    filesComponent: FilesComponent,
    serializationComponent: SerializationComponent,
) : DataComponent,
    FilesComponent by filesComponent,
    SerializationComponent by serializationComponent {

    override val keyValueStore: KeyValueStore by provider {
        FilesKeyValueStore(
            fileSystem = fileSystem,
            folder = keyStorePath,
            cbor = cbor,
        )
    }
}
