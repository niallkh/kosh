package kosh.presentation.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.entities.AccountEntity
import kosh.domain.failure.AppFailure
import kosh.domain.usecases.account.AccountService
import kosh.presentation.PerformAction
import kosh.presentation.di.di

@Composable
fun rememberDeleteAccount(
    id: AccountEntity.Id,
    accountService: AccountService = di { domain.accountService },
): DeleteAccountState {

    val delete = PerformAction<Unit, AppFailure> {
        accountService.delete(id)
    }

    return DeleteAccountState(
        deleted = delete.performed,
        loading = delete.inProgress,
        delete = { delete(Unit) },
    )
}

@Immutable
data class DeleteAccountState(
    val deleted: Boolean,
    val loading: Boolean,
    val delete: () -> Unit,
)
