package kosh.presentation.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.WalletEntity
import kosh.domain.serializers.ImmutableList
import kosh.domain.serializers.ImmutableSet
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.accountsByWallet
import kosh.domain.state.activeAccountIds
import kosh.domain.utils.optic
import kosh.presentation.di.di
import kotlinx.collections.immutable.PersistentList

@Composable
fun rememberWallets(
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): WalletState {
    val accountsByWallet by appStateProvider.collectAsState().optic(AppState.accountsByWallet())
    val enabled by appStateProvider.collectAsState().optic(AppState.activeAccountIds())

    return WalletState(
        wallets = accountsByWallet,
        enabled = enabled,
    )
}

@Immutable
class WalletState(
    val wallets: ImmutableList<Pair<WalletEntity, PersistentList<AccountEntity>>>,
    val enabled: ImmutableSet<AccountEntity.Id>,
)
