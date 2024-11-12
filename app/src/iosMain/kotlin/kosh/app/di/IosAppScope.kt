package kosh.app.di

import co.touchlab.crashkios.crashlytics.enableCrashlytics
import co.touchlab.crashkios.crashlytics.setCrashlyticsUnhandledExceptionHook
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.crashlytics.CrashlyticsLogWriter
import co.touchlab.kermit.platformLogWriter
import kosh.app.IosPushNotifier
import kosh.app.di.impl.DefaultAppScope
import kosh.data.DataComponent
import kosh.data.reown.ReownComponent
import kosh.domain.AppRepositoriesComponent
import kosh.domain.core.provider
import kotlinx.coroutines.launch

public class IosAppScope(
    override val reownComponent: ReownComponent,
) : DefaultAppScope(), IosComponent {

    override val appComponent: AppComponent by provider {
        IosAppComponent()
    }

    override val iosPushNotifier: IosPushNotifier by provider {
        IosPushNotifier(domain.notificationService)
    }

    override val fileSystemComponent: FileSystemComponent by provider {
        IosFileSystemComponent()
    }

    override val networkComponent: NetworkComponent by provider {
        IosNetworkComponent(
            filesComponent = filesComponent,
            appComponent = appComponent,
        )
    }

    override val imageComponent: ImageComponent by provider {
        IosImageComponent(
            networkComponent = networkComponent,
            filesComponent = filesComponent
        )
    }

    override val transportComponent: TransportComponent by provider {
        IosTransportComponent()
    }

    override val dataComponent: DataComponent by provider {
        IosDataComponent(
            serializationComponent = serializationComponent,
            filesComponent = filesComponent,
        )
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
            appComponent = appComponent,
            keystoneComponent = keystoneComponent,
        )
    }

    init {
        Logger.setTag("[K]")

        enableCrashlytics()
        setCrashlyticsUnhandledExceptionHook()

        if (appComponent.debug) {
            Logger.setMinSeverity(Severity.Verbose)
            Logger.setLogWriters(
                platformLogWriter(),
                CrashlyticsLogWriter()
            )
        } else {
            Logger.setMinSeverity(Severity.Info)
            Logger.setLogWriters(
                CrashlyticsLogWriter()
            )
        }


    }

    init {
        coroutinesComponent.applicationScope.launch {
            iosPushNotifier.start()
        }
    }
}
