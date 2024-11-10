package kosh.app.di

import kosh.app.di.impl.DefaultDataComponent
import kosh.domain.core.provider
import kosh.libs.keystore.IosKeyStore
import kosh.libs.keystore.KeyStore

internal class IosDataComponent(
    filesComponent: FilesComponent,
    serializationComponent: SerializationComponent,
) : DefaultDataComponent(filesComponent, serializationComponent) {

    override val keyStore: KeyStore by provider {
        IosKeyStore()
    }
}
