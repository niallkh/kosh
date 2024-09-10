package kosh.data.trezor

import arrow.core.Either
import arrow.core.right
import co.touchlab.kermit.Logger
import kosh.domain.entities.WalletEntity
import kosh.domain.entities.WalletEntity.Location
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.Hash
import kosh.domain.models.account.DerivationPath
import kosh.domain.models.account.ethereumDerivationPath
import kosh.domain.models.trezor.Trezor
import kosh.domain.models.web3.Signature
import kosh.domain.models.web3.Signer
import kosh.domain.models.web3.TransactionData
import kosh.domain.models.web3.gasPrice
import kosh.domain.repositories.TrezorListener
import kosh.domain.repositories.TrezorRepo
import kosh.eth.abi.Eip712
import kosh.eth.proposals.eip55.eip55
import kosh.eth.wallet.transaction.Transaction.Type1559
import kosh.libs.trezor.TrezorManager
import kosh.libs.trezor.cmds.ethereumAddress
import kosh.libs.trezor.cmds.signPersonalMessage
import kosh.libs.trezor.cmds.signTransaction
import kosh.libs.trezor.cmds.signTypedMessage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okio.ByteString.Companion.encodeUtf8
import kosh.eth.wallet.Wallet as SignerWallet

class DefaultTrezorRepo(
    private val trezorManager: TrezorManager,
    private val trezorOffChain: TrezorOffChain,
) : TrezorRepo {

    private val logger = Logger.withTag("[K]TrezorRepo")

    override val list: Flow<ImmutableList<Trezor>>
        get() = trezorManager.devices
            .map { devices ->
                devices.map { Trezor(Trezor.Id(it.id), it.product) }.toImmutableList()
            }
            .flowOn(Dispatchers.Default)

    override fun accounts(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        paths: List<DerivationPath>,
    ): Flow<Either<TrezorFailure, Signer>> = flow {
        coroutineScope {

            val networkDefinition = async {
//                trezorOffChain.getNetworkDefinition(zeroDerivationPath)
                null
            }

            trezorManager.open(
                id = trezor.id.value,
                listener = TrezorListenerAdapter(listener)
            ).use { connection ->
                val features = connection.init(newSession = refresh)

                val mainAddress = connection.ethereumAddress(
                    derivationPath = ethereumDerivationPath().path,
                    networkDefinition = networkDefinition.await(),
                )

                val location = Location.Trezor(
                    mainAddress = Address(mainAddress).getOrNull()!!,
                    product = trezor.product,
                    name = features.label,
                    model = features.model,
                    color = features.unit_color,
                )

                listener.onConnected(WalletEntity.Id(location))

                suspend fun derive(
                    derivationPath: DerivationPath,
                ): Signer {
                    val address = connection.ethereumAddress(
                        derivationPath = derivationPath.path,
                        networkDefinition = networkDefinition.await(),
                    )
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
    }
        .flowOn(Dispatchers.Default)
        .catchTrezorFailure(logger)

    override suspend fun signPersonalMessage(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        message: String,
        derivationPath: DerivationPath,
    ): Either<TrezorFailure, Signature> = withContext(Dispatchers.Default) {
        Either.catch {
            val networkDefinition = async {
//            getNetworkDefinition(derivationPath)
                null
            }

            trezorManager.open(
                id = trezor.id.value,
                listener = TrezorListenerAdapter(listener),
            ).use { connection ->
                connection.init(newSession = refresh)

                val mainAddress = connection.ethereumAddress(
                    derivationPath = ethereumDerivationPath().path,
                    networkDefinition = networkDefinition.await(),
                )

                listener.onConnected(WalletEntity.Id(Address(mainAddress).getOrNull()!!))

                val bytes = message.encodeUtf8()

                val (signature, address) = connection.signPersonalMessage(
                    derivationPath = derivationPath.path,
                    message = bytes,
                    networkDefinition = networkDefinition.await(),
                )

                val hash = SignerWallet.personalHash(bytes)

                val recovered = SignerWallet.recover(
                    signature = signature,
                    messageHash = hash
                )

                check(address == recovered.eip55())

                Signature(
                    signer = Address(ByteString(recovered.value)),
                    data = ByteString(signature),
                    hash = Hash(ByteString(hash))
                )
            }
        }
            .mapTrezorFailure(logger)
    }

    override suspend fun signTypedMessage(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        json: String,
        derivationPath: DerivationPath,
    ): Either<TrezorFailure, Signature> = withContext(Dispatchers.Default) {
        val networkDefinition = async {
//            getNetworkDefinition(derivationPath)
            null
        }

        Either.catch {
            trezorManager.open(
                id = trezor.id.value,
                listener = TrezorListenerAdapter(listener),
            ).use { connection ->
                connection.init(newSession = refresh)

                val mainAddress = connection.ethereumAddress(
                    derivationPath = ethereumDerivationPath().path,
                    networkDefinition = networkDefinition.await(),
                )

                listener.onConnected(WalletEntity.Id(Address(mainAddress).getOrNull()!!))

                val eip712 = Eip712.fromJson(json)
                val messageHash = SignerWallet.typeDataHash(eip712)

                val (signature, address) = connection.signTypedMessage(
                    derivationPath = derivationPath.path,
                    eip712 = eip712,
                    networkDefinition = networkDefinition.await(),
                    tokenDefinition = null,
                )

                val recovered = SignerWallet.recover(
                    signature = signature,
                    messageHash = messageHash
                )

                check(address == recovered.eip55())

                Signature(
                    signer = Address(ByteString(recovered.value)),
                    data = ByteString(signature),
                    hash = Hash(ByteString(messageHash)),
                )
            }
        }
            .mapTrezorFailure(logger)
    }

    override suspend fun signTransaction(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        transaction: TransactionData,
        derivationPath: DerivationPath,
    ): Either<TrezorFailure, Signature> = withContext(Dispatchers.Default) {
        Either.catch {
            val networkDefinition = async {
                trezorOffChain.getNetworkDefinition(transaction.tx.chainId)
            }

            val tokenDefinition = async {
                transaction.tx.to?.let {
                    trezorOffChain.getTokenDefinition(transaction.tx.chainId, it)
                }
            }

            trezorManager.open(
                id = trezor.id.value,
                listener = TrezorListenerAdapter(listener)
            ).use { connection ->
                connection.init(newSession = refresh)

                val mainAddress = connection.ethereumAddress(
                    derivationPath = ethereumDerivationPath().path,
                    networkDefinition = networkDefinition.await(),
                )

                listener.onConnected(WalletEntity.Id(Address(mainAddress).getOrNull()!!))

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
                    derivationPath = derivationPath.path,
                    transaction = type1559,
                    networkDefinition = networkDefinition.await(),
                    tokenDefinition = tokenDefinition.await(),
                )

                val encodedTransaction = SignerWallet.encode1559Transaction(
                    signature = signature,
                    transaction = type1559,
                )

                val recovered = SignerWallet.recover(
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
            .mapTrezorFailure(logger)
    }
}
