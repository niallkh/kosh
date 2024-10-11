package kosh.app.di.impl

import kosh.app.di.AppScope
import kosh.app.di.CoroutinesComponent
import kosh.app.di.FilesComponent
import kosh.app.di.SerializationComponent
import kosh.data.trezor.LedgerComponent
import kosh.data.trezor.TrezorComponent
import kosh.data.web3.Web3Component
import kosh.datastore.DataStoreComponent
import kosh.domain.DomainComponent
import kosh.domain.UiRepositoriesComponent
import kosh.domain.core.provider

public abstract class DefaultAppScope : AppScope {

    override val filesComponent: FilesComponent by provider {
        DefaultFilesComponent(
            fileSystemComponent = fileSystemComponent
        )
    }

    override val dataStoreComponent: DataStoreComponent by provider {
        DefaultDataStoreComponent(
            filesComponent = filesComponent,
            serializationComponent = serializationComponent,
            appComponent = appComponent,
        )
    }

    override val web3Component: Web3Component by provider {
        DefaultWeb3Component(
            networkComponent = networkComponent,
            serializationComponent = serializationComponent,
        )
    }

    override val coroutinesComponent: CoroutinesComponent by provider {
        DefaultCoroutinesComponent()
    }

    override val serializationComponent: SerializationComponent by provider {
        DefaultSerializationComponent()
    }

    override val domainComponent: DomainComponent by provider {
        DefaultDomainComponent(
            applicationScope = coroutinesComponent.applicationScope,
            appRepositoriesComponent = appRepositoriesComponent,
            uiRepositoriesComponent = object : UiRepositoriesComponent {},
        )
    }

    override val trezorComponent: TrezorComponent by provider {
        DefaultTrezorComponent(
            transportComponent = transportComponent,
        )
    }

    override val ledgerComponent: LedgerComponent by provider {
        DefaultLedgerComponent(
            transportComponent = transportComponent
        )
    }
}
