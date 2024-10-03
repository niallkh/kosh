package kosh.domain.usecases.trezor

import kosh.domain.models.hw.Trezor
import kosh.domain.repositories.TrezorRepo
import kosh.domain.serializers.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlin.time.Duration.Companion.milliseconds

class DefaultTrezorService(
    private val trezorRepo: TrezorRepo,
) : TrezorService {

    override fun list(): Flow<ImmutableList<Trezor>> = trezorRepo.list
        .distinctUntilChanged()
        .debounce(300.milliseconds)
        .onStart { emit(persistentListOf()) }
}
