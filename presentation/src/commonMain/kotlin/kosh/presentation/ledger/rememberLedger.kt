package kosh.presentation.ledger

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kosh.domain.models.ledger.Ledger
import kosh.domain.usecases.ledger.LedgerService
import kosh.presentation.di.di

@Composable
fun rememberLedger(
    ledgerService: LedgerService = di { domain.ledgerService },
): LedgerState {
    val ledger by ledgerService.getCurrentDevice().collectAsState(null)

    return LedgerState(
        ledger = ledger
    )
}

@Immutable
data class LedgerState(
    val ledger: Ledger?,
)
