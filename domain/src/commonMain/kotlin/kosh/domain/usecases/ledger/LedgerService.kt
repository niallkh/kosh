package kosh.domain.usecases.ledger

import kosh.domain.models.hw.Ledger
import kosh.domain.serializers.ImmutableList
import kotlinx.coroutines.flow.Flow

interface LedgerService {

    fun list(): Flow<ImmutableList<Ledger>>
}

