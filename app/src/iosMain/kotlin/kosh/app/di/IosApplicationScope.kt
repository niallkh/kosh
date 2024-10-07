package kosh.app.di

import kosh.app.di.impl.DefaultApplicationScope
import kosh.app.di.impl.DefaultRouteScopeFactory
import kosh.app.di.impl.IosAppComponent
import kosh.app.di.impl.IosAppRepositoriesComponent
import kosh.app.di.impl.IosDataComponent
import kosh.app.di.impl.IosFileSystemComponent
import kosh.app.di.impl.IosImageComponent
import kosh.app.di.impl.IosNetworkComponent
import kosh.app.di.impl.IosTransportComponent
import kosh.app.di.impl.IosWindowRepositoriesComponent
import kosh.data.DataComponent
import kosh.data.reown.ReownComponent
import kosh.domain.AppRepositoriesComponent
import kosh.domain.WindowRepositoriesComponent
import kosh.domain.core.provider
import kosh.presentation.di.RouteScopeFactory

class IosApplicationScope(
    override val reownComponent: ReownComponent,
) : DefaultApplicationScope(),
    WindowScope {

    override val appComponent: AppComponent by provider {
        IosAppComponent()
    }

    override val fileSystemComponent: FileSystemComponent by provider {
        IosFileSystemComponent()
    }

    override val networkComponent: NetworkComponent by provider {
        IosNetworkComponent(filesComponent)
    }

    override val imageComponent: ImageComponent by provider {
        IosImageComponent(networkComponent, filesComponent)
    }

    override val dataComponent: DataComponent by provider {
        IosDataComponent(dataStoreComponent)
    }

    override val transportComponent: TransportComponent by provider {
        IosTransportComponent()
    }

    override val routeScopeFactory: RouteScopeFactory by provider {
        DefaultRouteScopeFactory(
            applicationScope = this,
            windowScope = this
        )
    }

    override val windowRepositoriesComponent: WindowRepositoriesComponent by provider {
        IosWindowRepositoriesComponent()
    }

    override val appRepositoriesComponent: AppRepositoriesComponent by provider {
        IosAppRepositoriesComponent(
            dataComponent = dataComponent,
            trezorComponent = trezorComponent,
            web3Component = web3Component,
            networkComponent = networkComponent,
            serializationComponent = serializationComponent,
            coroutinesComponent = coroutinesComponent,
            filesComponent = filesComponent,
            ledgerComponent = ledgerComponent,
            reownComponent = reownComponent,
        )
    }
}

