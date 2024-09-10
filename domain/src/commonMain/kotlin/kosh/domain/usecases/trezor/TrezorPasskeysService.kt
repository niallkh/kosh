package kosh.domain.usecases.trezor

import arrow.core.Either
import arrow.core.raise.Raise
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.trezor.TrezorPasskey
import kosh.domain.repositories.TrezorListener
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.coroutines.flow.Flow

interface TrezorPasskeysService {

    fun get(
        listener: TrezorListener,
    ): Flow<Either<TrezorFailure, List<TrezorPasskey>>>

    suspend fun delete(
        raise: Raise<TrezorFailure>,
        listener: TrezorListener,
        indices: ImmutableSet<TrezorPasskey.Index>,
    )

    suspend fun export(
        passkeys: ImmutableList<TrezorPasskey>,
    )

    suspend fun import(
        raise: Raise<TrezorFailure>,
        listener: TrezorListener,
    )
}
