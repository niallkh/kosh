package kosh.presentation.ledger

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import arrow.core.toOption
import kosh.domain.failure.AppFailure
import kosh.domain.models.ledger.Ledger
import kosh.domain.usecases.ledger.LedgerService
import kosh.presentation.Collect
import kosh.presentation.di.di
import kotlinx.coroutines.flow.map

@Composable
fun rememberLedger(
    ledgerService: LedgerService = di { domain.ledgerService },
): LedgerState {
    val ledger = Collect<AppFailure, _> {
        ledgerService.getCurrentDevice()
            .map { it.toOption() }
    }

    return LedgerState(
        ledger = ledger.content?.getOrNull()
    )
}

@Immutable
data class LedgerState(
    val ledger: Ledger?,
)
