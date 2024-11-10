package kosh.app.di

import kosh.app.di.impl.DefaultAppRepositoriesComponent
import kosh.data.DataComponent
import kosh.data.reown.ReownComponent
import kosh.data.trezor.LedgerComponent
import kosh.data.trezor.TrezorComponent
import kosh.data.web3.Web3Component
import kosh.domain.AppRepositoriesComponent

internal class AndroidAppRepositoriesComponent(
    androidComponent: AndroidComponent,
    dataComponent: DataComponent,
    trezorComponent: TrezorComponent,
    web3Component: Web3Component,
    networkComponent: NetworkComponent,
    serializationComponent: SerializationComponent,
    coroutinesComponent: CoroutinesComponent,
    filesComponent: FilesComponent,
    ledgerComponent: LedgerComponent,
    reownComponent: ReownComponent,
    appComponent: AppComponent,
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
        reownComponent = reownComponent,
        appComponent = appComponent,
    ),
    AndroidComponent by androidComponent
