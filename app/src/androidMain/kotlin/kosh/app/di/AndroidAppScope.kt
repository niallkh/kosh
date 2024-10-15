package kosh.app.di

import android.content.Context
import kosh.app.AndroidPushNotifier
import kosh.app.BuildConfig
import kosh.app.di.impl.DefaultAppScope
import kosh.app.di.impl.DefaultLedgerComponent
import kosh.app.di.impl.DefaultTrezorComponent
import kosh.data.DataComponent
import kosh.data.reown.ReownComponent
import kosh.data.trezor.LedgerComponent
import kosh.data.trezor.TrezorComponent
import kosh.domain.AppRepositoriesComponent
import kosh.domain.core.provider
import kotlinx.coroutines.launch

internal class AndroidAppScope(
    override val context: Context,
) : DefaultAppScope(), AndroidAppComponent {

    override val androidPushNotifier: AndroidPushNotifier by provider {
        AndroidPushNotifier(
            context = context,
            notificationService = domain.notificationService,
        )
    }

    override val appComponent: AppComponent
        get() = this

    override val debug: Boolean
        get() = BuildConfig.DEBUG

    override val fileSystemComponent: FileSystemComponent by provider {
        AndroidFileSystemComponent(
            androidComponent = this,
        )
    }

    override val transportComponent: TransportComponent by provider {
        AndroidTransportComponent(
            androidComponent = this
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

    override val imageComponent: ImageComponent by provider {
        AndroidImageComponent(
            networkComponent = networkComponent,
            androidComponent = this,
            filesComponent = filesComponent,
        )
    }

    override val reownComponent: ReownComponent by provider {
        AndroidReownComponent(
            coroutinesComponent = coroutinesComponent,
            androidComponent = this,
        )
    }

    override val dataComponent: DataComponent by provider {
        AndroidDataComponent(
            androidComponent = this,
            dataStoreComponent = dataStoreComponent,
            filesComponent = filesComponent,
        )
    }

    override val appRepositoriesComponent: AppRepositoriesComponent by provider {
        AndroidAppRepositoriesComponent(
            dataComponent = dataComponent,
            trezorComponent = trezorComponent,
            web3Component = web3Component,
            androidComponent = this,
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
            androidPushNotifier.start()
        }
    }
}

