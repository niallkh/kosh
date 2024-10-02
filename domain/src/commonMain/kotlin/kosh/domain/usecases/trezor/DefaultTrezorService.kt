package kosh.domain.usecases.trezor

import kosh.domain.models.hw.Trezor
import kosh.domain.repositories.TrezorRepo
import kotlinx.coroutines.flow.Flow

class DefaultTrezorService(
    private val trezorRepo: TrezorRepo,
) : TrezorService {

    override fun list(): Flow<List<Trezor>> = trezorRepo.list
}
