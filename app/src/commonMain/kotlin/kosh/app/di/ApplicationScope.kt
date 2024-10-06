package kosh.app.di

import kosh.data.DataComponent
import kosh.data.trezor.LedgerComponent
import kosh.data.trezor.TrezorComponent
import kosh.data.wc2.ReownComponent
import kosh.data.web3.Web3Component
import kosh.datastore.DataStoreComponent
import kosh.domain.AppRepositoriesComponent
import kosh.domain.DomainComponent

interface ApplicationScope {
    val appComponent: AppComponent
    val serializationComponent: SerializationComponent
    val appRepositoriesComponent: AppRepositoriesComponent
    val coroutinesComponent: CoroutinesComponent
    val fileSystemComponent: FileSystemComponent
    val filesComponent: FilesComponent
    val networkComponent: NetworkComponent
    val dataComponent: DataComponent
    val trezorComponent: TrezorComponent
    val ledgerComponent: LedgerComponent
    val dataStoreComponent: DataStoreComponent
    val web3Component: Web3Component
    val imageComponent: ImageComponent
    val domainComponent: DomainComponent
    val transportComponent: TransportComponent
    val reownComponent: ReownComponent
}

