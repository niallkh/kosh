package kosh.presentation.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kosh.domain.entities.AccountEntity
import kosh.domain.models.Address
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.account
import kosh.domain.state.activeAccounts
import kosh.domain.utils.optic
import kosh.presentation.di.di

@Composable
fun rememberAccount(
    address: Address,
) = rememberAccount(AccountEntity.Id(address))

@Composable
fun rememberAccount(
    id: AccountEntity.Id,
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): Account {
    val account by appStateProvider.collectAsState().optic(AppState.account(id))
    val activeAccounts by appStateProvider.collectAsState().optic(AppState.activeAccounts())
    val enabled by remember { derivedStateOf { account?.id in activeAccounts.map { it.id } } }

    return Account(
        entity = account,
        enabled = enabled,
    )
}

@Immutable
data class Account(
    val entity: AccountEntity?,
    val enabled: Boolean,
)
