package kosh.presentation.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.entities.AccountEntity
import kosh.domain.usecases.account.AccountService
import kosh.presentation.core.di
import kosh.presentation.rememberEffect

@Composable
fun rememberDeleteAccount(
    id: AccountEntity.Id,
    accountService: AccountService = di { domain.accountService },
): DeleteAccountState {

    val delete = rememberEffect(id) {
        accountService.delete(id)
    }

    return DeleteAccountState(
        deleted = delete.done,
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
