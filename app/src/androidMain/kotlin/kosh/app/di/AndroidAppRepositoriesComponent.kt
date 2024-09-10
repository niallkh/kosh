package kosh.app.di

import kosh.app.BuildConfig
import kosh.app.di.impl.DefaultAppRepositoriesComponent
import kosh.app.firebase.FirebaseAnalyticsRepo
import kosh.data.DataComponent
import kosh.data.trezor.LedgerComponent
import kosh.data.trezor.TrezorComponent
import kosh.data.wc2.AndroidWcListener
import kosh.data.wc2.AndroidWcRepo
import kosh.data.wc2.WcConnectionController
import kosh.data.web3.Web3Component
import kosh.domain.AppRepositoriesComponent
import kosh.domain.analytics.AnalyticsRepo
import kosh.domain.core.provider
import kosh.domain.repositories.WcRepo

class AndroidAppRepositoriesComponent(
    androidComponent: AndroidComponent,
    dataComponent: DataComponent,
    trezorComponent: TrezorComponent,
    web3Component: Web3Component,
    networkComponent: NetworkComponent,
    serializationComponent: SerializationComponent,
    coroutinesComponent: CoroutinesComponent,
    filesComponent: FilesComponent,
    ledgerComponent: LedgerComponent,
) : AppRepositoriesComponent,
    DefaultAppRepositoriesComponent(
        dataComponent = dataComponent,
        trezorComponent = trezorComponent,
        web3Component = web3Component,
        networkComponent = networkComponent,
        serializationComponent = serializationComponent,
        coroutinesComponent = coroutinesComponent,
        filesComponent = filesComponent,
        ledgerComponent = ledgerComponent,
    ),
    AndroidComponent by androidComponent {

    override val wcRepo: WcRepo by provider {
        AndroidWcRepo(
            context = context,
            projectId = BuildConfig.WC_PROJECT,
            wcListener = AndroidWcListener(),
            wcConnectionController = WcConnectionController(),
            json = serializationComponent.json,
        )
    }

    override val analyticsRepo: AnalyticsRepo
        get() = FirebaseAnalyticsRepo
}
