package kosh.presentation.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import kosh.domain.entities.AccountEntity
import kosh.domain.models.Address
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.account
import kosh.domain.state.isActive
import kosh.domain.utils.optic
import kosh.presentation.core.di

@Composable
fun rememberAccount(
    address: Address,
) = rememberAccount(AccountEntity.Id(address))

@Composable
fun rememberAccount(
    id: AccountEntity.Id,
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): Account {
    val account by optic(AppState.account(id)) { appStateProvider.state }
    val active by optic(AppState.isActive(id)) { appStateProvider.state }

    return Account(
        entity = account,
        enabled = active,
    )
}

@Immutable
data class Account(
    val entity: AccountEntity?,
    val enabled: Boolean,
)
