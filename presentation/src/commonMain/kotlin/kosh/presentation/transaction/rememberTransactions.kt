package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kosh.domain.entities.TransactionEntity
import kosh.domain.serializers.ImmutableList
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.activeTransactions
import kosh.domain.utils.optic
import kosh.presentation.di.di

@Composable
fun rememberTransactions(
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): TransactionsState {
    val transactions by appStateProvider.collectAsState().optic(AppState.activeTransactions())

    return TransactionsState(
        txs = transactions,
    )
}

@Immutable
data class TransactionsState(
    val txs: ImmutableList<TransactionEntity>,
)
