package kosh.domain.usecases.trezor

import kosh.domain.models.hw.Trezor
import kosh.domain.serializers.ImmutableList
import kotlinx.coroutines.flow.Flow

interface TrezorService {

    fun list(): Flow<ImmutableList<Trezor>>
}
