package kosh.app.di

import android.content.Context
import kosh.app.AndroidPushNotifier
import kosh.app.di.impl.AndroidDataComponent
import kosh.app.di.impl.AndroidFileSystemComponent
import kosh.app.di.impl.AndroidImageComponent
import kosh.app.di.impl.AndroidNetworkComponent
import kosh.app.di.impl.AndroidTrezorComponent
import kosh.app.di.impl.DefaultApplicationScope
import kosh.app.kosh.app.di.impl.AndroidLedgerComponent
import kosh.app.kosh.app.di.impl.AndroidUsbComponent
import kosh.data.DataComponent
import kosh.data.trezor.LedgerComponent
import kosh.data.trezor.TrezorComponent
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

    override val usbComponent: UsbComponent by provider {
        AndroidUsbComponent(androidComponent = androidComponent)
    }

    override val trezorComponent: TrezorComponent by provider {
        AndroidTrezorComponent(
            usbComponent = usbComponent
        )
    }

    override val ledgerComponent: LedgerComponent by provider {
        AndroidLedgerComponent(
            usbComponent = usbComponent,
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
        )
    }

    override val imageComponent: ImageComponent by provider {
        AndroidImageComponent(
            networkComponent = networkComponent,
            androidComponent = androidComponent,
            filesComponent = filesComponent,
        )
    }
}

interface AndroidComponent {
    val context: Context
    val androidPushNotifier: AndroidPushNotifier
}
