package kosh.domain.usecases.trezor

import kosh.domain.models.hw.Trezor
import kotlinx.coroutines.flow.Flow

interface TrezorService {

    fun list(): Flow<List<Trezor>>
}
