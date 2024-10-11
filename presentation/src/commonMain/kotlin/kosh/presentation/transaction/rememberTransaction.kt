package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kosh.domain.entities.TransactionEntity
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.transaction
import kosh.domain.utils.optic
import kosh.presentation.core.di

@Composable
fun rememberTransaction(
    id: TransactionEntity.Id,
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): TransactionState {
    val tx by appStateProvider.collectAsState().optic(AppState.transaction(id))

    return TransactionState(
        entity = tx,
    )
}

@Immutable
data class TransactionState(
    val entity: TransactionEntity?,
)
