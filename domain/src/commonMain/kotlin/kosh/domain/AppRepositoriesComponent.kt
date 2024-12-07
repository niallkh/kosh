package kosh.domain

import kosh.domain.analytics.AnalyticsRepo
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

interface AppRepositoriesComponent {
    val trezorRepo: TrezorRepo
    val trezorPasskeysRepo: TrezorPasskeysRepo
    val ledgerRepo: LedgerRepo
    val keyStoreRepo: KeyStoreRepo
    val keystoneRepo: KeystoneRepo
    val networkRepo: NetworkRepo
    val tokenRepo: TokenRepo
    val analyticsRepo: AnalyticsRepo
    val tokenBalanceRepo: TokenBalanceRepo
    val gasRepo: GasRepo
    val transactionRepo: TransactionRepo
    val tokenListsRepo: TokenListsRepo
    val notificationRepo: NotificationRepo
    val referenceRepo: ReferenceRepo
    val functionSignatureRepo: FunctionSignatureRepo
    val wcRepo: WcRepo
    val deeplinkRepo: DeeplinkRepo

    val appStateRepo: AppStateRepo
}
