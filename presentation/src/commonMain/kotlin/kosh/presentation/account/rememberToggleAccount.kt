package kosh.presentation.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.entities.AccountEntity
import kosh.domain.failure.AppFailure
import kosh.domain.usecases.account.AccountService
import kosh.presentation.PerformAction
import kosh.presentation.di.di

@Composable
fun rememberToggleAccount(
    accountService: AccountService = di { domain.accountService },
): ToggleAccountState {
    val toggle = PerformAction<Pair<AccountEntity.Id, Boolean>, AppFailure> { (id, enabled) ->
        accountService.toggle(id, enabled)
    }

    return ToggleAccountState(
        toggle = { id, enabled ->
            toggle(id to enabled)
        },
        toggled = toggle.performed,
    )
}

@Immutable
data class ToggleAccountState(
    val toggle: (AccountEntity.Id, Boolean) -> Unit,
    val toggled: Boolean,
)