package kosh.domain.usecases.keystone

import arrow.core.raise.either
import arrow.core.raise.ensure
import kosh.domain.failure.KeystoneFailure
import kosh.domain.models.Address
import kosh.domain.models.account.ethereumDerivationPath
import kosh.domain.models.hw.Keystone
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.JsonTypedData
import kosh.domain.models.web3.Signature
import kosh.domain.models.web3.Signer
import kosh.domain.models.web3.TransactionData
import kosh.domain.repositories.KeystoneListener
import kosh.domain.repositories.KeystoneRepo
import kosh.domain.serializers.Either
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.account
import kotlinx.coroutines.flow.Flow

class DefaultKeystoneAccountService(
    private val keystoneRepo: KeystoneRepo,
    private val appStateProvider: AppStateProvider,
) : KeystoneAccountService {

    override fun getAccounts(
        listener: KeystoneListener,
        keystone: Keystone,
        refresh: Boolean,
        fromIndex: UInt,
        amount: UInt,
    ): Flow<Either<KeystoneFailure, Signer>> =
        keystoneRepo.accounts(
            listener = listener,
            keystone = keystone,
            paths = (fromIndex..<(fromIndex + amount)).map { ethereumDerivationPath(it) },
        )

    override suspend fun sign(
        listener: KeystoneListener,
        keystone: Keystone,
        address: Address,
        message: EthMessage,
    ): Either<KeystoneFailure, Signature> = either {
        val appState = appStateProvider.state
        val account = AppState.account(address).get(appState)
            ?: raise(KeystoneFailure.Other())

        val signature = keystoneRepo.signPersonalMessage(
            listener = listener,
            keystone = keystone,
            message = message,
            derivationPath = account.derivationPath,
        ).bind()

        ensure(signature.signer == address) {
            KeystoneFailure.InvalidState()
        }

        signature
    }

    override suspend fun sign(
        listener: KeystoneListener,
        keystone: Keystone,
        address: Address,
        jsonTypeData: JsonTypedData,
    ): Either<KeystoneFailure, Signature> = either {
        val appState = appStateProvider.state
        val account = AppState.account(address).get(appState)
            ?: raise(KeystoneFailure.Other())

        val signature = keystoneRepo.signTypedMessage(
            listener = listener,
            keystone = keystone,
            jsonTypedData = jsonTypeData,
            derivationPath = account.derivationPath,
        ).bind()

        ensure(signature.signer == address) {
            KeystoneFailure.InvalidState()
        }

        signature
    }

    override suspend fun sign(
        listener: KeystoneListener,
        keystone: Keystone,
        transaction: TransactionData,
    ): Either<KeystoneFailure, Signature> = either {
        val appState = appStateProvider.state
        val account = AppState.account(transaction.tx.from).get(appState)
            ?: raise(KeystoneFailure.Other())

        val signature = keystoneRepo.signTransaction(
            listener = listener,
            keystone = keystone,
            transaction = transaction,
            derivationPath = account.derivationPath,
        ).bind()

        ensure(signature.signer == transaction.tx.from) {
            KeystoneFailure.InvalidState()
        }

        signature
    }
}
