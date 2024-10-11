package kosh.app.di

import kosh.app.di.impl.DefaultAppScope
import kosh.data.DataComponent
import kosh.data.reown.ReownComponent
import kosh.domain.AppRepositoriesComponent
import kosh.domain.core.provider

public class IosAppScope(
    override val reownComponent: ReownComponent,
) : DefaultAppScope() {

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

