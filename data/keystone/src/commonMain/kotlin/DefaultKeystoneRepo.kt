package kosh.data.keystone

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.right
import co.touchlab.kermit.Logger
import kosh.domain.entities.WalletEntity.Location
import kosh.domain.failure.KeystoneFailure
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.Hash
import kosh.domain.models.account.DerivationPath
import kosh.domain.models.account.ethereumDerivationPath
import kosh.domain.models.hw.HardwareWallet
import kosh.domain.models.hw.Keystone
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.JsonTypedData
import kosh.domain.models.web3.Signature
import kosh.domain.models.web3.Signer
import kosh.domain.models.web3.TransactionData
import kosh.domain.models.web3.gasPrice
import kosh.domain.repositories.KeystoneListener
import kosh.domain.repositories.KeystoneRepo
import kosh.eth.abi.eip712.Eip712
import kosh.eth.wallet.Wallet
import kosh.eth.wallet.transaction.Transaction.Type1559
import kosh.eth.wallet.transaction.encode
import kosh.libs.keystone.KeystoneManager
import kosh.libs.keystone.cmds.ethereumAddress
import kosh.libs.keystone.cmds.signPersonalMessage
import kosh.libs.keystone.cmds.signTransaction
import kosh.libs.keystone.cmds.signTypedMessage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.io.bytestring.encodeToByteString

class DefaultKeystoneRepo(
    private val keystoneManager: KeystoneManager,
) : KeystoneRepo {

    private val logger = Logger.withTag("[K]KeystoneRepo")

    override val list: Flow<ImmutableList<Keystone>>
        get() = keystoneManager.devices
            .map { devices ->
                devices.map { Keystone(HardwareWallet.Id(it.id), it.product) }.toImmutableList()
            }
            .flowOn(Dispatchers.Default)

    override fun accounts(
        listener: KeystoneListener,
        keystone: Keystone,
        paths: List<DerivationPath>,
    ): Flow<Either<KeystoneFailure, Signer>> = flow {
        keystoneManager.open(
            id = keystone.id.value,
            listener = KeystoneListenerAdapter(listener),
        ).use { connection ->

            val mainAddress = connection.ethereumAddress(ethereumDerivationPath().components)

            val location = Location.Keystone(
                mainAddress = Address(mainAddress).getOrNull()!!,
                product = keystone.product,
            )

            suspend fun derive(
                derivationPath: DerivationPath,
            ): Signer {
                val address = connection.ethereumAddress(derivationPath.components)

                return Signer(
                    derivationPath = derivationPath,
                    location = location,
                    address = Address(address).getOrNull()!!
                )
            }

            paths.forEach { derivationPath ->
                emit(derive(derivationPath).right())
            }
        }
    }
        .flowOn(Dispatchers.Default)
        .catchKeystoneFailure(logger)

    override suspend fun signPersonalMessage(
        listener: KeystoneListener,
        keystone: Keystone,
        message: EthMessage,
        derivationPath: DerivationPath,
    ): Either<KeystoneFailure, Signature> = withContext(Dispatchers.Default) {
        either {
            arrow.core.raise.catch({
                doSignPersonalMessage(keystone, listener, message, derivationPath)
            }) {
                raise(it.mapKeystoneFailure(logger))
            }
        }
    }

    private suspend fun Raise<KeystoneFailure>.doSignPersonalMessage(
        keystone: Keystone,
        listener: KeystoneListener,
        message: EthMessage,
        derivationPath: DerivationPath,
    ) = keystoneManager.open(
        id = keystone.id.value,
        listener = KeystoneListenerAdapter(listener),
    ).use { connection ->

        val bytes = message.value.encodeToByteString()

        val signature = connection.signPersonalMessage(
            derivationPath = derivationPath.components,
            message = bytes,
        )

        val hash = Wallet.personalHash(bytes)

        val recovered = Wallet.recover(
            signature = signature,
            messageHash = hash
        )

        Signature(
            signer = Address(ByteString(recovered.value)),
            data = ByteString(signature),
            hash = Hash(ByteString(hash))
        )
    }


    override suspend fun signTypedMessage(
        listener: KeystoneListener,
        keystone: Keystone,
        jsonTypedData: JsonTypedData,
        derivationPath: DerivationPath,
    ): Either<KeystoneFailure, Signature> = withContext(Dispatchers.Default) {
        either {
            arrow.core.raise.catch({
                doSignTypedMessage(listener, keystone, jsonTypedData, derivationPath)
            }) {
                raise(it.mapKeystoneFailure(logger))
            }
        }
    }

    private suspend fun Raise<KeystoneFailure>.doSignTypedMessage(
        listener: KeystoneListener,
        keystone: Keystone,
        jsonTypedData: JsonTypedData,
        derivationPath: DerivationPath,
    ): Signature = keystoneManager.open(
        id = keystone.id.value,
        listener = KeystoneListenerAdapter(listener),
    ).use { connection ->

        val signature = connection.signTypedMessage(
            derivationPath = derivationPath.components,
            eip712 = jsonTypedData.json.encodeToByteString(),
        )

        val messageHash = Wallet.typeDataHash(Eip712.fromJson(jsonTypedData.json))

        val recovered = Wallet.recover(
            signature = signature,
            messageHash = messageHash
        )

        Signature(
            signer = Address(ByteString(recovered.value)),
            data = ByteString(signature),
            hash = Hash(ByteString(messageHash))
        )
    }


    override suspend fun signTransaction(
        listener: KeystoneListener,
        keystone: Keystone,
        transaction: TransactionData,
        derivationPath: DerivationPath,
    ): Either<KeystoneFailure, Signature> = withContext(Dispatchers.Default) {
        either {
            arrow.core.raise.catch({
                doSignTransaction(listener, keystone, transaction, derivationPath)
            }) {
                raise(it.mapKeystoneFailure(logger))
            }
        }
    }

    private suspend fun Raise<KeystoneFailure>.doSignTransaction(
        listener: KeystoneListener,
        keystone: Keystone,
        transaction: TransactionData,
        derivationPath: DerivationPath,
    ): Signature = keystoneManager.open(
        id = keystone.id.value,
        listener = KeystoneListenerAdapter(listener),
    ).use { connection ->

        val type1559 = Type1559(
            chainId = transaction.tx.chainId.value,
            data = transaction.tx.input.bytes(),
            maxFeePerGas = transaction.gasPrice.gasPrice,
            maxPriorityFeePerGas = transaction.gasPrice.priority,
            to = transaction.tx.to?.bytes(),
            nonce = transaction.nonce,
            value = transaction.tx.value,
            gasLimit = transaction.gasLimit
        )

        val signature = connection.signTransaction(
            derivationPath = derivationPath.components,
            rawTransaction = type1559.encode(),
        )

        val encodedTransaction = Wallet.encode1559Transaction(
            signature = signature,
            transaction = type1559,
        )

        val recovered = Wallet.recover(
            signature = signature,
            messageHash = encodedTransaction.messageHash
        )

        Signature(
            signer = Address(ByteString(recovered.value)),
            data = ByteString(encodedTransaction.data),
            hash = Hash(ByteString(encodedTransaction.messageHash))
        )
    }
}
