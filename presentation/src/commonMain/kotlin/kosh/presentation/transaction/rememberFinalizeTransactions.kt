package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.Nel
import arrow.core.mapOrAccumulate
import arrow.core.raise.recover
import arrow.core.recover
import kosh.domain.entities.TransactionEntity
import kosh.domain.failure.TransactionFailure
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.activeTransactions
import kosh.domain.usecases.transaction.Eip1559TransactionService
import kosh.domain.utils.optic
import kosh.presentation.di.di
import kosh.presentation.di.rememberLifecycleState

@Composable
fun rememberFinalizeTransactions(
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
    transactionService: Eip1559TransactionService = di { domain.eip1559TransactionService },
): FinalizeTransactions {
    val transactions by appStateProvider.collectAsState().optic(AppState.activeTransactions())
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<Nel<TransactionFailure>?>(null) }
    var retry by remember { mutableIntStateOf(0) }

    if (rememberLifecycleState()) {
        LaunchedEffect(retry, transactions) {
            loading = true
            failure = null

            recover({
                transactions.asSequence()
                    .mapNotNull { it as? TransactionEntity.Eip1559 }
                    .filter { it.receipt == null }
                    .toList()
                    .mapOrAccumulate {
                        transactionService.finalize(it.id).recover { failure ->
                            when (failure) {
                                is TransactionFailure.ReceiptNotAvailable -> Unit
                                else -> raise(failure)
                            }
                        }
                            .bind()
                    }.bind()

                loading = false
                failure = null
            }) {
                failure = it
                loading = false
            }
        }
    }

    return FinalizeTransactions(
        loading = loading,
        failures = failure,
        retry = { retry++ }
    )
}

data class FinalizeTransactions(
    val loading: Boolean,
    val failures: Nel<TransactionFailure>?,
    val retry: () -> Unit,
)
