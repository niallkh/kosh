package kosh.domain.usecases.trezor

import arrow.core.Either
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.Address
import kosh.domain.models.trezor.Trezor
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.JsonTypeData
import kosh.domain.models.web3.Signature
import kosh.domain.models.web3.Signer
import kosh.domain.models.web3.TransactionData
import kosh.domain.repositories.TrezorListener
import kotlinx.coroutines.flow.Flow


interface TrezorAccountService {

    fun getAccounts(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        fromIndex: UInt,
        amount: UInt,
    ): Flow<Either<TrezorFailure, Signer>>

    suspend fun sign(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        address: Address,
        message: EthMessage,
    ): Either<TrezorFailure, Signature>

    suspend fun sign(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        address: Address,
        jsonTypeData: JsonTypeData,
    ): Either<TrezorFailure, Signature>

    suspend fun sign(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        transaction: TransactionData,
    ): Either<TrezorFailure, Signature>
}

