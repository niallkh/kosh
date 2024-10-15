package kosh.domain.usecases.ledger

import kosh.domain.models.hw.Ledger
import kosh.domain.repositories.LedgerRepo
import kosh.domain.serializers.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.time.Duration.Companion.milliseconds

class DefaultLedgerService(
    private val ledgerRepo: LedgerRepo,
) : LedgerService {

    override fun list(): Flow<ImmutableList<Ledger>> = ledgerRepo.list
        .distinctUntilChanged()
        .debounce(300.milliseconds)
}
