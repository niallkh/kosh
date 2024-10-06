package kosh.app.di

import android.content.Context
import kosh.app.AndroidPushNotifier
import kosh.app.di.impl.AndroidDataComponent
import kosh.app.di.impl.AndroidFileSystemComponent
import kosh.app.di.impl.AndroidImageComponent
import kosh.app.di.impl.AndroidNetworkComponent
import kosh.app.di.impl.AndroidReownComponent
import kosh.app.di.impl.AndroidTransportComponent
import kosh.app.di.impl.DefaultApplicationScope
import kosh.app.di.impl.DefaultLedgerComponent
import kosh.app.di.impl.DefaultTrezorComponent
import kosh.data.DataComponent
import kosh.data.trezor.LedgerComponent
import kosh.data.trezor.TrezorComponent
import kosh.data.wc2.ReownComponent
import kosh.domain.AppRepositoriesComponent
import kosh.domain.core.provider

class AndroidApplicationScope(
    override val context: Context,
) : DefaultApplicationScope(), AndroidComponent {

    override val androidPushNotifier: AndroidPushNotifier by provider {
        AndroidPushNotifier(
            context = context,
            notificationService = domainComponent.notificationService,
            applicationScope = coroutinesComponent.applicationScope,
        )
    }

    private val androidComponent: AndroidComponent
        get() = this

    override val appComponent: AppComponent by provider {
        AndroidAppComponent()
    }

    override val fileSystemComponent: FileSystemComponent by provider {
        AndroidFileSystemComponent(
            androidComponent = androidComponent,
        )
    }

    override val transportComponent: TransportComponent by provider {
        AndroidTransportComponent(
            androidComponent = androidComponent
        )
    }

    override val trezorComponent: TrezorComponent by provider {
        DefaultTrezorComponent(
            transportComponent = transportComponent
        )
    }

    override val ledgerComponent: LedgerComponent by provider {
        DefaultLedgerComponent(
            transportComponent = transportComponent,
        )
    }

    override val networkComponent: NetworkComponent by provider {
        AndroidNetworkComponent(
            filesComponent = filesComponent,
        )
    }

    override val dataComponent: DataComponent by provider {
        AndroidDataComponent(
            androidComponent = androidComponent,
            dataStoreComponent = dataStoreComponent,
            filesComponent = filesComponent,
        )
    }

    override val imageComponent: ImageComponent by provider {
        AndroidImageComponent(
            networkComponent = networkComponent,
            androidComponent = androidComponent,
            filesComponent = filesComponent,
        )
    }

    override val reownComponent: ReownComponent by provider {
        AndroidReownComponent(
            coroutinesComponent = coroutinesComponent,
            androidComponent = androidComponent,
        )
    }

    override val appRepositoriesComponent: AppRepositoriesComponent by provider {
        AndroidAppRepositoriesComponent(
            dataComponent = dataComponent,
            trezorComponent = trezorComponent,
            web3Component = web3Component,
            androidComponent = androidComponent,
            networkComponent = networkComponent,
            serializationComponent = serializationComponent,
            coroutinesComponent = coroutinesComponent,
            filesComponent = filesComponent,
            ledgerComponent = ledgerComponent,
            reownComponent = reownComponent,
        )
    }
}

interface AndroidComponent {
    val context: Context
    val androidPushNotifier: AndroidPushNotifier
}
