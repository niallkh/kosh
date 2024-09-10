package kosh.domain.repositories

import arrow.core.Either
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.trezor.Trezor
import kosh.domain.models.trezor.TrezorPasskey
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.coroutines.flow.Flow

interface TrezorPasskeysRepo : Repository {

    suspend fun deletePasskey(
        listener: TrezorListener,
        trezor: Trezor,
        indices: ImmutableSet<TrezorPasskey.Index>,
    ): Either<TrezorFailure, Unit>

    suspend fun addPasskeys(
        listener: TrezorListener,
        trezor: Trezor,
        ids: ImmutableList<TrezorPasskey.Id>,
    ): Either<TrezorFailure, Unit>

    suspend fun encodePasskeys(
        passkeys: ImmutableList<TrezorPasskey>,
    ): String

    suspend fun decodePasskeys(
        content: String,
    ): ImmutableList<TrezorPasskey>

    fun passkeys(
        listener: TrezorListener,
        trezor: Trezor,
    ): Flow<Either<TrezorFailure, TrezorPasskey>>
}
