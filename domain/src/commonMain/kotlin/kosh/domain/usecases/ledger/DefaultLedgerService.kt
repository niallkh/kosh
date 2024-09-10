package kosh.domain.usecases.ledger

import kosh.domain.models.ledger.Ledger
import kosh.domain.repositories.LedgerRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DefaultLedgerService(
    ledgerRepo: LedgerRepo,
    applicationScope: CoroutineScope,
) : LedgerService {

    val list = ledgerRepo.list.map { it.firstOrNull() }
        .distinctUntilChanged()
        .stateIn(applicationScope, SharingStarted.Lazily, null)

    override fun getCurrentDevice(): Flow<Ledger?> = list
}
