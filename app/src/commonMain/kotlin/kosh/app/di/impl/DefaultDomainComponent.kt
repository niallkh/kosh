package kosh.app.di.impl

import kosh.domain.AppRepositoriesComponent
import kosh.domain.DomainComponent
import kosh.domain.UiRepositoriesComponent
import kosh.domain.core.provider
import kosh.domain.state.AppStateProvider
import kosh.domain.state.DefaultAppStateProvider
import kosh.domain.usecases.account.AccountService
import kosh.domain.usecases.account.AccountTokensDiscoveryService
import kosh.domain.usecases.account.DefaultAccountService
import kosh.domain.usecases.account.DefaultAccountTokensDiscoveryService
import kosh.domain.usecases.keystone.DefaultKeystoneAccountService
import kosh.domain.usecases.keystone.DefaultKeystoneService
import kosh.domain.usecases.keystone.KeystoneAccountService
import kosh.domain.usecases.keystone.KeystoneService
import kosh.domain.usecases.ledger.DefaultLedgerAccountService
import kosh.domain.usecases.ledger.DefaultLedgerService
import kosh.domain.usecases.ledger.LedgerAccountService
import kosh.domain.usecases.ledger.LedgerService
import kosh.domain.usecases.network.DefaultNetworkService
import kosh.domain.usecases.network.NetworkService
import kosh.domain.usecases.notification.DefaultNotificationService
import kosh.domain.usecases.notification.NotificationService
import kosh.domain.usecases.reown.DefaultWcAuthenticationService
import kosh.domain.usecases.reown.DefaultWcProposalService
import kosh.domain.usecases.reown.DefaultWcRequestService
import kosh.domain.usecases.reown.WcAuthenticationService
import kosh.domain.usecases.reown.WcNotificationsService
import kosh.domain.usecases.reown.WcProposalService
import kosh.domain.usecases.reown.WcRequestService
import kosh.domain.usecases.reown.WcSessionService
import kosh.domain.usecases.token.DefaultTokenBalanceService
import kosh.domain.usecases.token.DefaultTokenDiscoveryService
import kosh.domain.usecases.token.DefaultTokenService
import kosh.domain.usecases.token.TokenBalanceService
import kosh.domain.usecases.token.TokenDiscoveryService
import kosh.domain.usecases.token.TokenService
import kosh.domain.usecases.transaction.ContractCallService
import kosh.domain.usecases.transaction.Eip1559TransactionService
import kosh.domain.usecases.transaction.PersonalMessageService
import kosh.domain.usecases.transaction.TransactionService
import kosh.domain.usecases.transaction.TypedDataService
import kosh.domain.usecases.transaction.parser.ApproveParser
import kosh.domain.usecases.transaction.parser.FallbackParser
import kosh.domain.usecases.transaction.parser.TransferParser
import kosh.domain.usecases.trezor.DefaultTrezorAccountService
import kosh.domain.usecases.trezor.DefaultTrezorPasskeysService
import kosh.domain.usecases.trezor.DefaultTrezorPassphraseService
import kosh.domain.usecases.trezor.DefaultTrezorService
import kosh.domain.usecases.trezor.TrezorAccountService
import kosh.domain.usecases.trezor.TrezorPasskeysService
import kosh.domain.usecases.trezor.TrezorPassphraseService
import kosh.domain.usecases.trezor.TrezorService
import kotlinx.coroutines.CoroutineScope

internal class DefaultDomainComponent(
    applicationScope: CoroutineScope,
    uiRepositoriesComponent: UiRepositoriesComponent,
    appRepositoriesComponent: AppRepositoriesComponent,
) : DomainComponent,
    UiRepositoriesComponent by uiRepositoriesComponent,
    AppRepositoriesComponent by appRepositoriesComponent {

    override val appStateProvider: AppStateProvider by provider {
        DefaultAppStateProvider(appStateRepo)
    }

    override val networkService: NetworkService by provider {
        DefaultNetworkService(
            appStateRepo = appStateRepo,
            networkRepo = networkRepo,
        )
    }

    override val accountService: AccountService by provider {
        DefaultAccountService(
            appStateRepo = appStateRepo,
        )
    }

    override val tokenService: TokenService by provider {
        DefaultTokenService(
            appStateRepo = appStateRepo,
        )
    }

    override val tokenDiscoveryService: TokenDiscoveryService by provider {
        DefaultTokenDiscoveryService(
            tokenRepo = tokenRepo,
            tokenListsRepo = tokenListsRepo,
            appStateProvider = appStateProvider
        )
    }

    override val tokenBalanceService: TokenBalanceService by provider {
        DefaultTokenBalanceService(
            appStateRepo = appStateRepo,
            tokenBalanceRepo = tokenBalanceRepo,
        )
    }

    override val notificationService: NotificationService by provider {
        DefaultNotificationService(
            notificationRepo = notificationRepo,
        )
    }

    override val eip1559TransactionService: Eip1559TransactionService by provider {
        Eip1559TransactionService(
            appStateRepo = appStateRepo,
            transactionRepo = transactionRepo,
            referenceRepo = referenceRepo,
        )
    }

    override val personalMessageService: PersonalMessageService by provider {
        PersonalMessageService(
            appStateRepo = appStateRepo,
            referenceRepo = referenceRepo
        )
    }

    override val typedDataService: TypedDataService by provider {
        TypedDataService(
            appStateRepo = appStateRepo,
            referenceRepo = referenceRepo
        )
    }

    override val transactionService: TransactionService by provider {
        TransactionService(
            appStateRepo = appStateRepo,
            applicationScope = applicationScope,
            referenceRepo = referenceRepo,
        )
    }

    override val callDataParserService: ContractCallService by provider {
        ContractCallService(
            parsers = listOf(
                TransferParser(
                    appStateProvider = appStateProvider,
                ),
                ApproveParser(
                    appStateProvider = appStateProvider,
                ),
                FallbackParser(
                    appStateProvider = appStateProvider,
                )
            )
        )
    }

    override val trezorPasskeysService: TrezorPasskeysService by provider {
        DefaultTrezorPasskeysService(
            trezorService = trezorService,
            trezorRepo = trezorPasskeysRepo,
            shareRepo = shareRepo,
        )
    }

    override val trezorPassphraseService: TrezorPassphraseService by provider {
        DefaultTrezorPassphraseService(
            keyStoreRepo = keyStoreRepo,
        )
    }

    override val trezorService: TrezorService by provider {
        DefaultTrezorService(
            trezorRepo = trezorRepo,
        )
    }

    override val trezorAccountService: TrezorAccountService by provider {
        DefaultTrezorAccountService(
            trezorRepo = trezorRepo,
            appStateRepo = appStateRepo,
        )
    }

    override val ledgerService: LedgerService by provider {
        DefaultLedgerService(
            ledgerRepo = ledgerRepo,
        )
    }

    override val ledgerAccountService: LedgerAccountService by provider {
        DefaultLedgerAccountService(
            ledgerRepo = ledgerRepo,
            appStateRepo = appStateRepo
        )
    }

    override val wcProposalService: WcProposalService by provider {
        DefaultWcProposalService(
            reownRepo = wcRepo,
            applicationScope = applicationScope,
            notificationService = notificationService,
            appStateProvider = appStateProvider
        )
    }

    override val wcSessionService: WcSessionService by provider {
        WcSessionService(
            reownRepo = wcRepo,
            appStateProvider = appStateProvider,
            applicationScope = applicationScope,
        )
    }

    override val wcRequestService: WcRequestService by provider {
        DefaultWcRequestService(
            reownRepo = wcRepo,
            applicationScope = applicationScope,
            notificationService = notificationService,
            sessionService = wcSessionService,
            appStateProvider = appStateProvider,
        )
    }

    override val wcAuthenticationService: WcAuthenticationService by provider {
        DefaultWcAuthenticationService(
            reownRepo = wcRepo,
            notificationService = notificationService,
            appStateProvider = appStateProvider,
        )
    }

    override val wcNotificationsService: WcNotificationsService by provider {
        WcNotificationsService(
            wcRepo = wcRepo,
            notificationService = notificationService,
            deeplinkRepo = deeplinkRepo,
        )
    }

    override val accountTokensDiscoveryService: AccountTokensDiscoveryService by provider {
        DefaultAccountTokensDiscoveryService(
            tokensDiscoveryService = tokenDiscoveryService,
            tokenBalanceService = tokenBalanceService,
            tokenService = tokenService,
            appStateProvider = appStateProvider,
        )
    }

    override val keystoneService: KeystoneService by provider {
        DefaultKeystoneService(
            keystoneRepo = keystoneRepo,
        )
    }

    override val keystoneAccountService: KeystoneAccountService by provider {
        DefaultKeystoneAccountService(
            keystoneRepo = keystoneRepo,
            appStateProvider = appStateProvider,
        )
    }
}
