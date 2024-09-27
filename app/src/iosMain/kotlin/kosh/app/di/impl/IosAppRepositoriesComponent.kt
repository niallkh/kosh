package kosh.app.di.impl

import kosh.app.di.CoroutinesComponent
import kosh.app.di.FilesComponent
import kosh.app.di.NetworkComponent
import kosh.app.di.SerializationComponent
import kosh.data.DataComponent
import kosh.data.trezor.LedgerComponent
import kosh.data.trezor.TrezorComponent
import kosh.data.wc2.IosWcRepo
import kosh.data.web3.Web3Component
import kosh.domain.AppRepositoriesComponent
import kosh.domain.core.provider
import kosh.domain.repositories.WcRepo

class IosAppRepositoriesComponent(
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
    ) {

    override val wcRepo: WcRepo by provider {
        IosWcRepo()
    }
}
