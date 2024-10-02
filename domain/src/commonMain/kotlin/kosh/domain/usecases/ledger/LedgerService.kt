package kosh.domain.usecases.ledger

import kosh.domain.models.hw.Ledger
import kotlinx.coroutines.flow.Flow

interface LedgerService {

    fun list(): Flow<List<Ledger>>
}

