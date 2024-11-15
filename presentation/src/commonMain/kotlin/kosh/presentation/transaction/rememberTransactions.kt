package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import kosh.domain.entities.TransactionEntity
import kosh.domain.serializers.ImmutableList
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.activeTransactions
import kosh.domain.utils.optic
import kosh.presentation.core.di

@Composable
fun rememberTransactions(
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): TransactionsState {
    val transactions by optic(AppState.activeTransactions()) { appStateProvider.state }

    return TransactionsState(
        transactions = transactions,
        init = appStateProvider.init
    )
}

@Immutable
data class TransactionsState(
    val transactions: ImmutableList<TransactionEntity>,
    val init: Boolean,
)
