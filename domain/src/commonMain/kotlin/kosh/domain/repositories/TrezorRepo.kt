package kosh.domain.repositories

import arrow.core.Either
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.account.DerivationPath
import kosh.domain.models.hw.Trezor
import kosh.domain.models.web3.Signature
import kosh.domain.models.web3.Signer
import kosh.domain.models.web3.TransactionData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface TrezorRepo : Repository {

    val list: Flow<ImmutableList<Trezor>>

    fun accounts(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        paths: List<DerivationPath>,
    ): Flow<Either<TrezorFailure, Signer>>

    suspend fun signPersonalMessage(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        message: String,
        derivationPath: DerivationPath,
    ): Either<TrezorFailure, Signature>

    suspend fun signTypedMessage(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        json: String,
        derivationPath: DerivationPath,
    ): Either<TrezorFailure, Signature>

    suspend fun signTransaction(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        transaction: TransactionData,
        derivationPath: DerivationPath,
    ): Either<TrezorFailure, Signature>
}
