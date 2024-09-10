package kosh.domain.usecases.trezor

import kosh.domain.models.trezor.Trezor
import kosh.domain.repositories.TrezorRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DefaultTrezorService(
    trezorRepo: TrezorRepo,
    applicationScope: CoroutineScope,
) : TrezorService {

    private val list = trezorRepo.list.map { it.firstOrNull() }
        .distinctUntilChanged()
        .stateIn(applicationScope, SharingStarted.Lazily, null)

    override fun getCurrentDevice(): Flow<Trezor?> = list
}
