package kosh.app.di

import kosh.app.IosPushNotifier
import kosh.app.di.impl.DefaultAppScope
import kosh.data.DataComponent
import kosh.data.reown.ReownComponent
import kosh.domain.AppRepositoriesComponent
import kosh.domain.core.provider
import kotlinx.coroutines.launch
import platform.Foundation.NSBundle

public class IosAppScope(
    override val reownComponent: ReownComponent,
) : DefaultAppScope(), IosAppComponent {

    override val appComponent: AppComponent
        get() = this

    override val debug: Boolean by provider {
        NSBundle.mainBundle.objectForInfoDictionaryKey("DEBUG") == "true"
    }

    override val iosPushNotifier: IosPushNotifier by provider {
        IosPushNotifier(domain.notificationService)
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

    override val transportComponent: TransportComponent by provider {
        IosTransportComponent()
    }

    override val dataComponent: DataComponent = IosDataComponent(dataStoreComponent)

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

    init {
        coroutinesComponent.applicationScope.launch {
            iosPushNotifier.start()
        }
    }
}
