package kosh.presentation.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.entities.AccountEntity
import kosh.domain.failure.AccountFailure
import kosh.domain.usecases.account.AccountService
import kosh.presentation.Perform
import kosh.presentation.di.di

@Composable
fun rememberUpdateAccount(
    id: AccountEntity.Id,
    accountService: AccountService = di { domain.accountService },
): UpdateAccountState {

    val update = Perform { name: String ->
        accountService.update(id, name).bind()
    }

    return UpdateAccountState(
        updated = update.performed,
        failure = update.failure,
        update = { update(it) },
        retry = { update.retry() }
    )
}

@Immutable
data class UpdateAccountState(
    val updated: Boolean,
    val failure: AccountFailure?,
    val update: (String) -> Unit,
    val retry: () -> Unit,
)
