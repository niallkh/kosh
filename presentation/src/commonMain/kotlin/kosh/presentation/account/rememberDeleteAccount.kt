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
    val delete = rememberEffect(id) { _: Unit ->
        accountService.delete(id)
    }

    return DeleteAccountState(
        deleted = delete.result != null,
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
