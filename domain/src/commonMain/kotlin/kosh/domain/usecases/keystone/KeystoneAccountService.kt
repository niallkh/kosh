package kosh.domain.usecases.keystone

import kosh.domain.failure.KeystoneFailure
import kosh.domain.models.Address
import kosh.domain.models.hw.Keystone
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.JsonTypedData
import kosh.domain.models.web3.Signature
import kosh.domain.models.web3.Signer
import kosh.domain.models.web3.TransactionData
import kosh.domain.repositories.KeystoneListener
import kosh.domain.serializers.Either
import kotlinx.coroutines.flow.Flow

interface KeystoneAccountService {

    fun getAccounts(
        listener: KeystoneListener,
        keystone: Keystone,
        refresh: Boolean,
        fromIndex: UInt,
        amount: UInt,
    ): Flow<Either<KeystoneFailure, Signer>>

    suspend fun sign(
        listener: KeystoneListener,
        keystone: Keystone,
        address: Address,
        message: EthMessage,
    ): Either<KeystoneFailure, Signature>

    suspend fun sign(
        listener: KeystoneListener,
        keystone: Keystone,
        address: Address,
        jsonTypeData: JsonTypedData,
    ): Either<KeystoneFailure, Signature>

    suspend fun sign(
        listener: KeystoneListener,
        keystone: Keystone,
        transaction: TransactionData,
    ): Either<KeystoneFailure, Signature>
}

