package kosh.app.di.impl

import kosh.app.DefaultAnalyticsRepo
import kosh.app.di.AppComponent
import kosh.app.di.CoroutinesComponent
import kosh.app.di.FilesComponent
import kosh.app.di.NetworkComponent
import kosh.app.di.SerializationComponent
import kosh.data.DataComponent
import kosh.data.DefaultAppStateRepo
import kosh.data.DefaultKeyStoreRepo
import kosh.data.DefaultNotificationRepo
import kosh.data.DefaultReferenceRepo
import kosh.data.keystone.DefaultKeystoneRepo
import kosh.data.keystone.KeystoneComponent
import kosh.data.reown.DefaultWcRepo
import kosh.data.reown.ReownComponent
import kosh.data.trezor.DefaultLedgerRepo
import kosh.data.trezor.DefaultTrezorPasskeysRepo
import kosh.data.trezor.DefaultTrezorRepo
import kosh.data.trezor.LedgerComponent
import kosh.data.trezor.LedgerOffChain
import kosh.data.trezor.TrezorComponent
import kosh.data.trezor.TrezorOffChain
import kosh.data.web3.DefaultFunctionSignatureRepo
import kosh.data.web3.DefaultGasRepo
import kosh.data.web3.DefaultNetworkRepo
import kosh.data.web3.DefaultTokenBalanceRepo
import kosh.data.web3.DefaultTokenRepo
import kosh.data.web3.DefaultTransactionRepo
import kosh.data.web3.Web3Component
import kosh.domain.AppRepositoriesComponent
import kosh.domain.analytics.AnalyticsRepo
import kosh.domain.core.provider
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.DeeplinkRepo
import kosh.domain.repositories.FunctionSignatureRepo
import kosh.domain.repositories.GasRepo
import kosh.domain.repositories.KeyStoreRepo
import kosh.domain.repositories.KeystoneRepo
import kosh.domain.repositories.LedgerRepo
import kosh.domain.repositories.NetworkRepo
import kosh.domain.repositories.NotificationRepo
import kosh.domain.repositories.ReferenceRepo
import kosh.domain.repositories.TokenBalanceRepo
import kosh.domain.repositories.TokenListsRepo
import kosh.domain.repositories.TokenRepo
import kosh.domain.repositories.TransactionRepo
import kosh.domain.repositories.TrezorPasskeysRepo
import kosh.domain.repositories.TrezorRepo
import kosh.domain.repositories.WcRepo
import kosh.domain.usecases.network.DefaultNetworkService
import kosh.domain.usecases.network.getRpcProvidersUC
import kosh.files.migration.migration1
import kosh.ui.navigation.DefaultDeeplinkRepo
import kosh.ui.resources.DefaultTokenListsRepo

internal abstract class DefaultAppRepositoriesComponent(
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
    keystoneComponent: KeystoneComponent,
) : AppRepositoriesComponent,
    DataComponent by dataComponent,
    TrezorComponent by trezorComponent,
    Web3Component by web3Component,
    NetworkComponent by networkComponent,
    SerializationComponent by serializationComponent,
    FilesComponent by filesComponent,
    CoroutinesComponent by coroutinesComponent,
    LedgerComponent by ledgerComponent,
    ReownComponent by reownComponent,
    AppComponent by appComponent,
    KeystoneComponent by keystoneComponent {

    private val networkService by provider {
        DefaultNetworkService(
            appStateRepo = appStateRepo,
            networkRepo = networkRepo,
        )
    }

    override val appStateRepo: AppStateRepo by provider {
        DefaultAppStateRepo(
            applicationScope = applicationScope,
            keyValueStore = keyValueStore,
            migrations = listOf(
                migration1(debug)
            )
        )
    }

    override val trezorRepo: TrezorRepo by provider {
        DefaultTrezorRepo(
            trezorManager = trezorManager,
            trezorOffChain = TrezorOffChain(httpClient)
        )
    }

    override val trezorPasskeysRepo: TrezorPasskeysRepo by provider {
        DefaultTrezorPasskeysRepo(
            trezorManager = trezorManager,
        )
    }

    override val ledgerRepo: LedgerRepo by provider {
        DefaultLedgerRepo(
            ledgerManager = ledgerManager,
            ledgerOffChain = LedgerOffChain(httpClient)
        )
    }

    override val keystoneRepo: KeystoneRepo by provider {
        DefaultKeystoneRepo(
            keystoneManager = keystoneManager,
        )
    }

    override val keyStoreRepo: KeyStoreRepo by provider {
        DefaultKeyStoreRepo(
            keyStore = keyStore
        )
    }

    override val networkRepo: NetworkRepo by provider {
        DefaultNetworkRepo(
            web3ProviderFactory = web3ProviderFactory,
        )
    }

    override val tokenBalanceRepo: TokenBalanceRepo by provider {
        DefaultTokenBalanceRepo(
            web3ProviderFactory = web3ProviderFactory,
            networkService = networkService,
        )
    }

    override val tokenRepo: TokenRepo by provider {
        DefaultTokenRepo(
            web3ProviderFactory = web3ProviderFactory,
            getRpcProvidersUC = getRpcProvidersUC(appStateRepo),
            httpClient = httpClient,
            json = json,
        )
    }

    override val gasRepo: GasRepo by provider {
        DefaultGasRepo(
            web3ProviderFactory = web3ProviderFactory,
            getRpcProvidersUC = getRpcProvidersUC(appStateRepo),
            networkService = networkService,
        )
    }

    override val transactionRepo: TransactionRepo by provider {
        DefaultTransactionRepo(
            web3ProviderFactory = web3ProviderFactory,
            getRpcProvidersUC = getRpcProvidersUC(appStateRepo),
            networkService = networkService
        )
    }

    override val tokenListsRepo: TokenListsRepo by provider {
        DefaultTokenListsRepo()
    }

    override val notificationRepo: NotificationRepo by provider {
        DefaultNotificationRepo()
    }

    override val referenceRepo: ReferenceRepo by provider {
        DefaultReferenceRepo(keyValueStore = keyValueStore)
    }

    override val functionSignatureRepo: FunctionSignatureRepo by provider {
        DefaultFunctionSignatureRepo(client = httpClient)
    }

    override val analyticsRepo: AnalyticsRepo
        get() = DefaultAnalyticsRepo

    override val wcRepo: WcRepo by provider {
        DefaultWcRepo(
            adapter = reownAdapter,
        )
    }

    override val deeplinkRepo: DeeplinkRepo by provider {
        DefaultDeeplinkRepo()
    }
}
