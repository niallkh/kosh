package kosh.domain.usecases.ledger

import kosh.domain.models.ledger.Ledger
import kotlinx.coroutines.flow.Flow

interface LedgerService {
    fun getCurrentDevice(): Flow<Ledger?>
}

