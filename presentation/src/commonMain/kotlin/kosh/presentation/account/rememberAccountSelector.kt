package kosh.presentation.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kosh.domain.entities.AccountEntity
import kosh.domain.serializers.ImmutableList
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.activeAccounts
import kosh.domain.utils.optic
import kosh.presentation.core.di
import kosh.presentation.di.rememberSerializable


@Composable
fun rememberAccountSelector(
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): AccountSelectorState {
    val accounts by optic(AppState.activeAccounts()) { appStateProvider.state }
    var selected by rememberSerializable { mutableStateOf(accounts.firstOrNull()) }

    return remember {
        object : AccountSelectorState {
            override val selected: AccountEntity? get() = selected
            override val available: ImmutableList<AccountEntity> get() = accounts
            override fun select(account: AccountEntity) {
                selected = account
            }
        }
    }
}

@Stable
interface AccountSelectorState {
    val selected: AccountEntity?
    val available: ImmutableList<AccountEntity>
    fun select(account: AccountEntity)
}
