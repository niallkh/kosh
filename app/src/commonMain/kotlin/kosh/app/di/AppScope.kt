package kosh.app.di

import kosh.data.DataComponent
import kosh.data.keystone.KeystoneComponent
import kosh.data.reown.ReownComponent
import kosh.data.trezor.LedgerComponent
import kosh.data.trezor.TrezorComponent
import kosh.data.web3.Web3Component
import kosh.domain.AppRepositoriesComponent
import kosh.domain.DomainComponent

public interface AppScope {
    public val appComponent: AppComponent
    public val serializationComponent: SerializationComponent
    public val appRepositoriesComponent: AppRepositoriesComponent
    public val coroutinesComponent: CoroutinesComponent
    public val fileSystemComponent: FileSystemComponent
    public val filesComponent: FilesComponent
    public val networkComponent: NetworkComponent
    public val dataComponent: DataComponent
    public val trezorComponent: TrezorComponent
    public val ledgerComponent: LedgerComponent
    public val keystoneComponent: KeystoneComponent
    public val web3Component: Web3Component
    public val imageComponent: ImageComponent
    public val domain: DomainComponent
    public val transportComponent: TransportComponent
    public val reownComponent: ReownComponent
}

