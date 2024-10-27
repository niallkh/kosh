package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import kosh.domain.entities.TransactionEntity
import kosh.domain.usecases.transaction.TransactionService
import kosh.presentation.core.di
import kosh.presentation.rememberEffect

@Composable
fun rememberDeleteTransaction(
    id: TransactionEntity.Id,
    transactionService: TransactionService = di { domain.transactionService },
): DeleteTransactionState {
    val delete = rememberEffect(id) {
        transactionService.delete(id)
    }

    return DeleteTransactionState(
        deleted = delete.done,
        loading = delete.inProgress,
        delete = { delete.invoke() }
    )
}

data class DeleteTransactionState(
    val deleted: Boolean,
    val loading: Boolean,
    val delete: () -> Unit,
)
