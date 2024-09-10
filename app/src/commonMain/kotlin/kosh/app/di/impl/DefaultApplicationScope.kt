package kosh.app.di.impl

import kosh.app.di.ApplicationScope
import kosh.app.di.CoroutinesComponent
import kosh.app.di.FilesComponent
import kosh.app.di.SerializationComponent
import kosh.data.web3.Web3Component
import kosh.datastore.DataStoreComponent
import kosh.domain.DomainComponent
import kosh.domain.WindowRepositoriesComponent
import kosh.domain.core.provider

abstract class DefaultApplicationScope : ApplicationScope {

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
            windowRepositoriesComponent = object : WindowRepositoriesComponent {},
        )
    }
}
