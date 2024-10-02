package kosh.domain.usecases.ledger

import kosh.domain.models.hw.Ledger
import kosh.domain.repositories.LedgerRepo
import kotlinx.coroutines.flow.Flow

class DefaultLedgerService(
    private val ledgerRepo: LedgerRepo,
) : LedgerService {

    override fun list(): Flow<List<Ledger>> = ledgerRepo.list
}
