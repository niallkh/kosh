package kosh.app.di

import kosh.app.di.impl.DefaultDataComponent
import kosh.domain.core.provider
import kosh.libs.keystore.AndroidRsaKeyStore
import kosh.libs.keystore.KeyStore

internal class AndroidDataComponent(
    androidComponent: AndroidComponent,
    filesComponent: FilesComponent,
    serializationComponent: SerializationComponent,
) : DefaultDataComponent(filesComponent, serializationComponent) {

    override val keyStore: KeyStore by provider {
        AndroidRsaKeyStore(
            context = androidComponent.context,
            produceFile = filesComponent.keyStorePath,
            invalidateByBiometric = true,
        )
    }
}
