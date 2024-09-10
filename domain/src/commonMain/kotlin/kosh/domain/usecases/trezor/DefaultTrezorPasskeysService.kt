package kosh.domain.usecases.trezor

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.raise.either
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.trezor.TrezorPasskey
import kosh.domain.repositories.ShareRepo
import kosh.domain.repositories.TrezorListener
import kosh.domain.repositories.TrezorPasskeysRepo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.plus
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningReduce
import kotlinx.coroutines.flow.take
import kotlinx.datetime.Clock

class DefaultTrezorPasskeysService(
    private val trezorService: TrezorService,
    private val trezorRepo: TrezorPasskeysRepo,
    private val shareRepo: ShareRepo,
) : TrezorPasskeysService {

    override fun get(
        listener: TrezorListener,
    ): Flow<Either<TrezorFailure, List<TrezorPasskey>>> = flow {
        trezorService.getCurrentDevice()
            .take(1)
            .flatMapLatest { deviceId ->
                if (deviceId != null) {
                    trezorRepo.passkeys(listener, deviceId)
                } else {
                    flowOf(TrezorFailure.NotConnected().left())
                }

            }
            .map { it.map(::persistentListOf) }
            .runningReduce { acc, value -> either { acc.bind() + value.bind() } }
            .collect(this)
    }

    override suspend fun delete(
        raise: Raise<TrezorFailure>,
        listener: TrezorListener,
        indices: ImmutableSet<TrezorPasskey.Index>,
    ): Unit = raise.run {
        val deviceId = trezorService.getCurrentDevice().first()
            ?: raise(TrezorFailure.NotConnected())
        trezorRepo.deletePasskey(listener, deviceId, indices)
    }

    override suspend fun export(
        passkeys: ImmutableList<TrezorPasskey>,
    ) {
        val json = trezorRepo.encodePasskeys(passkeys)

        shareRepo.shareJson(
            json = json,
            name = "trezor-passkeys-${Clock.System.now().epochSeconds}.json"
        )
    }

    override suspend fun import(
        raise: Raise<TrezorFailure>,
        listener: TrezorListener,
    ): Unit = raise.run {
        val json = shareRepo.importJson()

        val passkeys = trezorRepo.decodePasskeys(json)

        val trezor = trezorService.getCurrentDevice().first()
            ?: raise(TrezorFailure.NotConnected())

        trezorRepo.addPasskeys(
            trezor = trezor,
            listener = listener,
            ids = passkeys.map { it.id }.toImmutableList()
        )
    }
}
