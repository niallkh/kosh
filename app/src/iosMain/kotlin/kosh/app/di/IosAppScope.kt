package kosh.app.di

import co.touchlab.crashkios.bugsnag.BugsnagConfiguration
import co.touchlab.crashkios.bugsnag.startBugsnag
import co.touchlab.kermit.Logger
import co.touchlab.kermit.NSLogWriter
import co.touchlab.kermit.Severity
import co.touchlab.kermit.bugsnag.BugsnagLogWriter
import kosh.app.IosPushNotifier
import kosh.app.di.impl.DefaultAppScope
import kosh.data.DataComponent
import kosh.data.reown.ReownComponent
import kosh.domain.AppRepositoriesComponent
import kosh.domain.core.provider
import kotlinx.coroutines.launch

public class IosAppScope(
    override val reownComponent: ReownComponent,
    bugsnagConfiguration: BugsnagConfiguration,
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
        IosDataComponent(dataStoreComponent = dataStoreComponent)
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

    init {
        startBugsnag(bugsnagConfiguration)

        Logger.setTag("[K]")
        if (appComponent.debug) {
            Logger.setMinSeverity(Severity.Verbose)
            Logger.setLogWriters(
                NSLogWriter(),
                BugsnagLogWriter(
                    minCrashSeverity = Severity.Error,
                )
            )
        } else {
            Logger.setMinSeverity(Severity.Info)
            Logger.setLogWriters(
                BugsnagLogWriter()
            )
        }

//        DecomposeExperimentFlags.duplicateConfigurationsEnabled = true
    }

    init {
        coroutinesComponent.applicationScope.launch {
            iosPushNotifier.start()
        }
    }
}
