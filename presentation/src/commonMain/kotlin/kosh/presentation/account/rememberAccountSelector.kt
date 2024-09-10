package kosh.presentation.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kosh.domain.entities.AccountEntity
import kosh.domain.serializers.ImmutableList
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.activeAccounts
import kosh.domain.utils.optic
import kosh.presentation.di.di
import kosh.presentation.di.rememberSerializable


@Composable
fun rememberAccountSelector(
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): AccountSelectorState {
    val accounts by appStateProvider.collectAsState().optic(AppState.activeAccounts())
    var selected by rememberSerializable { mutableStateOf(accounts.firstOrNull()) }

    return AccountSelectorState(
        selected = selected,
        available = accounts,
        select = { selected = it }
    )
}

@Immutable
data class AccountSelectorState(
    val selected: AccountEntity?,
    val available: ImmutableList<AccountEntity>,
    val select: (AccountEntity) -> Unit,
)
