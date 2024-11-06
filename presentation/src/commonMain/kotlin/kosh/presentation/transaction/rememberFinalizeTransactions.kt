package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import kosh.domain.state.transactionsKey
import kosh.domain.usecases.transaction.Eip1559TransactionService
import kosh.domain.utils.optic
import kosh.presentation.component.selector.selector
import kosh.presentation.core.di
import kosh.presentation.di.rememberLifecycleState
import kosh.presentation.ticker.rememberTimer
import kosh.presentation.ticker.runAtLeast
import kotlin.time.Duration.Companion.minutes

@Composable
fun rememberFinalizeTransactions(
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
    transactionService: Eip1559TransactionService = di { domain.eip1559TransactionService },
): FinalizeTransactions {
    val transactions by appStateProvider.collectAsState().optic(AppState.activeTransactions())
    var loading by remember { mutableStateOf(false) }
    var refreshing by remember { mutableStateOf(false) }
    var failures by remember { mutableStateOf<Nel<TransactionFailure>?>(null) }
    val timer = rememberTimer(1.minutes)

    if (appStateProvider.init && rememberLifecycleState()) {
        LaunchedEffect(Unit) {
            while (true) {
                timer.waitNext()

                loading = true

                recover({
                    runAtLeast {
                        val notFinalizedTransactions = transactions.asSequence()
                            .mapNotNull { it as? TransactionEntity.Eip1559 }
                            .filter { it.receipt == null }
                            .toList()

                        notFinalizedTransactions
                            .mapOrAccumulate {
                                transactionService.finalize(it.id).recover { failure ->
                                    when (failure) {
                                        is TransactionFailure.ReceiptNotAvailable -> Unit
                                        else -> raise(failure)
                                    }
                                }.bind()
                            }.bind()
                    }

                    loading = false
                    refreshing = false
                    failures = null
                }) {
                    failures = it
                    refreshing = false
                    loading = false
                }
            }
        }
    }

    appStateProvider.collectAsState().optic(AppState.transactionsKey()).selector {
        timer.reset()
    }

    return FinalizeTransactions(
        loading = loading,
        refreshing = refreshing,
        failures = failures,
        refresh = {
            refreshing = true
            timer.reset()
        }
    )
}

data class FinalizeTransactions(
    val loading: Boolean,
    val refreshing: Boolean,
    val failures: Nel<TransactionFailure>?,
    val refresh: () -> Unit,
)
