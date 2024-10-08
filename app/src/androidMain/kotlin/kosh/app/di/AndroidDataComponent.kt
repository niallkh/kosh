package kosh.app.di

import kosh.data.DataComponent
import kosh.data.sources.AppStateSource
import kosh.datastore.DataStoreComponent
import kosh.datastore.DefaultAppStateSource
import kosh.domain.core.provider
import kosh.libs.keystore.AndroidRsaKeyStore
import kosh.libs.keystore.KeyStore

class AndroidDataComponent(
    androidComponent: AndroidComponent,
    dataStoreComponent: DataStoreComponent,
    filesComponent: FilesComponent,
) : DataComponent {
    override val appStateSource: AppStateSource by provider {
        DefaultAppStateSource(
            dataStore = dataStoreComponent.appStateDataStore
        )
    }

    override val keyStore: KeyStore by provider {
        AndroidRsaKeyStore(
            context = androidComponent.context,
            produceFile = filesComponent.keyStorePath,
            invalidateByBiometric = true,
        )
    }
}
