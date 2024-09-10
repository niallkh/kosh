package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import kosh.domain.entities.TransactionEntity
import kosh.domain.failure.AppFailure
import kosh.domain.usecases.transaction.TransactionService
import kosh.presentation.PerformAction
import kosh.presentation.di.di

@Composable
fun rememberDeleteTransaction(
    id: TransactionEntity.Id,
    transactionService: TransactionService = di { domain.transactionService },
): DeleteTransactionState {
    val delete = PerformAction<Unit, AppFailure>(id) {
        transactionService.delete(id)
    }

    return DeleteTransactionState(
        deleted = delete.performed,
        loading = delete.inProgress,
        delete = { delete(Unit) }
    )
}

data class DeleteTransactionState(
    val deleted: Boolean,
    val loading: Boolean,
    val delete: () -> Unit,
)
