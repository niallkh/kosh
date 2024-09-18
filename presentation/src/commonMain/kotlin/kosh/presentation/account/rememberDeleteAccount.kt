package kosh.presentation.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.entities.AccountEntity
import kosh.domain.usecases.account.AccountService
import kosh.presentation.Perform
import kosh.presentation.di.di
import kosh.presentation.invoke

@Composable
fun rememberDeleteAccount(
    id: AccountEntity.Id,
    accountService: AccountService = di { domain.accountService },
): DeleteAccountState {

    val delete = Perform {
        accountService.delete(id)
    }

    return DeleteAccountState(
        deleted = delete.performed,
        loading = delete.inProgress,
        delete = { delete() },
    )
}

@Immutable
data class DeleteAccountState(
    val deleted: Boolean,
    val loading: Boolean,
    val delete: () -> Unit,
)
