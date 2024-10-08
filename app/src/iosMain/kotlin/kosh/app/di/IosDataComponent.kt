package kosh.app.di

import kosh.data.DataComponent
import kosh.data.sources.AppStateSource
import kosh.datastore.DataStoreComponent
import kosh.datastore.DefaultAppStateSource
import kosh.domain.core.provider
import kosh.libs.keystore.IosKeyStore
import kosh.libs.keystore.KeyStore

class IosDataComponent(
    dataStoreComponent: DataStoreComponent,
) : DataComponent {
    override val appStateSource: AppStateSource by provider {
        DefaultAppStateSource(
            dataStore = dataStoreComponent.appStateDataStore
        )
    }

    override val keyStore: KeyStore by provider {
        IosKeyStore()
    }
}
