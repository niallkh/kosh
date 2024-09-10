package kosh.domain.usecases.trezor

import kosh.domain.models.trezor.Trezor
import kotlinx.coroutines.flow.Flow

interface TrezorService {

    fun getCurrentDevice(): Flow<Trezor?>
}
