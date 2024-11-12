package kosh.domain

import kosh.domain.state.AppStateProvider
import kosh.domain.usecases.account.AccountService
import kosh.domain.usecases.account.AccountTokensDiscoveryService
import kosh.domain.usecases.keystone.KeystoneAccountService
import kosh.domain.usecases.keystone.KeystoneService
import kosh.domain.usecases.ledger.LedgerAccountService
import kosh.domain.usecases.ledger.LedgerService
import kosh.domain.usecases.network.NetworkService
import kosh.domain.usecases.notification.NotificationService
import kosh.domain.usecases.reown.WcAuthenticationService
import kosh.domain.usecases.reown.WcNotificationsService
import kosh.domain.usecases.reown.WcProposalService
import kosh.domain.usecases.reown.WcRequestService
import kosh.domain.usecases.reown.WcSessionService
import kosh.domain.usecases.token.TokenBalanceService
import kosh.domain.usecases.token.TokenDiscoveryService
import kosh.domain.usecases.token.TokenService
import kosh.domain.usecases.transaction.ContractCallService
import kosh.domain.usecases.transaction.Eip1559TransactionService
import kosh.domain.usecases.transaction.PersonalMessageService
import kosh.domain.usecases.transaction.TransactionService
import kosh.domain.usecases.transaction.TypedTransactionService
import kosh.domain.usecases.trezor.TrezorAccountService
import kosh.domain.usecases.trezor.TrezorPasskeysService
import kosh.domain.usecases.trezor.TrezorPassphraseService
import kosh.domain.usecases.trezor.TrezorService

interface DomainComponent {
    val appStateProvider: AppStateProvider

    val networkService: NetworkService
    val accountService: AccountService
    val tokenService: TokenService
    val tokenDiscoveryService: TokenDiscoveryService
    val tokenBalanceService: TokenBalanceService
    val eip1559TransactionService: Eip1559TransactionService
    val personalMessageService: PersonalMessageService
    val typedTransactionService: TypedTransactionService
    val transactionService: TransactionService
    val notificationService: NotificationService
    val callDataParserService: ContractCallService
    val accountTokensDiscoveryService: AccountTokensDiscoveryService

    val trezorPasskeysService: TrezorPasskeysService
    val trezorPassphraseService: TrezorPassphraseService
    val trezorService: TrezorService
    val trezorAccountService: TrezorAccountService

    val ledgerService: LedgerService
    val ledgerAccountService: LedgerAccountService

    val keystoneService: KeystoneService
    val keystoneAccountService: KeystoneAccountService

    val wcSessionService: WcSessionService
    val wcProposalService: WcProposalService
    val wcRequestService: WcRequestService
    val wcAuthenticationService: WcAuthenticationService
    val wcNotificationsService: WcNotificationsService
}

