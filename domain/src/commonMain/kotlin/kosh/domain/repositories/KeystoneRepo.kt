package kosh.domain.repositories

import arrow.core.Either
import kosh.domain.failure.KeystoneFailure
import kosh.domain.models.account.DerivationPath
import kosh.domain.models.hw.Keystone
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.JsonTypedData
import kosh.domain.models.web3.Signature
import kosh.domain.models.web3.Signer
import kosh.domain.models.web3.TransactionData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface KeystoneRepo {
    val list: Flow<ImmutableList<Keystone>>

    fun accounts(
        listener: KeystoneListener,
        keystone: Keystone,
        paths: List<DerivationPath>,
    ): Flow<Either<KeystoneFailure, Signer>>

    suspend fun signPersonalMessage(
        listener: KeystoneListener,
        keystone: Keystone,
        message: EthMessage,
        derivationPath: DerivationPath,
    ): Either<KeystoneFailure, Signature>

    suspend fun signTypedMessage(
        listener: KeystoneListener,
        keystone: Keystone,
        jsonTypedData: JsonTypedData,
        derivationPath: DerivationPath,
    ): Either<KeystoneFailure, Signature>

    suspend fun signTransaction(
        listener: KeystoneListener,
        keystone: Keystone,
        transaction: TransactionData,
        derivationPath: DerivationPath,
    ): Either<KeystoneFailure, Signature>
}
