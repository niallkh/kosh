package kosh.data.trezor

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.raise.catch
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import co.touchlab.kermit.Logger
import kosh.domain.entities.WalletEntity.Location
import kosh.domain.failure.LedgerFailure
import kosh.domain.failure.RequestCanceledException
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.Hash
import kosh.domain.models.account.DerivationPath
import kosh.domain.models.account.ledgerDerivationPath
import kosh.domain.models.ledger.Ledger
import kosh.domain.models.web3.Signature
import kosh.domain.models.web3.Signer
import kosh.domain.models.web3.TransactionData
import kosh.domain.models.web3.gasPrice
import kosh.domain.repositories.LedgerListener
import kosh.domain.repositories.LedgerRepo
import kosh.eth.abi.Eip712
import kosh.eth.abi.Eip712V2
import kosh.eth.abi.Value
import kosh.eth.wallet.Wallet
import kosh.eth.wallet.transaction.Transaction.Type1559
import kosh.eth.wallet.transaction.encode
import kosh.libs.ledger.LedgerManager
import kosh.libs.ledger.cmds.ethereumAddress
import kosh.libs.ledger.cmds.getAppAndVersion
import kosh.libs.ledger.cmds.signPersonalMessage
import kosh.libs.ledger.cmds.signTransaction
import kosh.libs.ledger.cmds.signTypedMessage
import kosh.libs.usb.PermissionNotGrantedException
import kosh.libs.usb.UsbInterfaceNotClaimedException
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okio.ByteString.Companion.encodeUtf8

class DefaultLedgerRepo(
    private val ledgerManager: LedgerManager,
    private val ledgerOffChain: LedgerOffChain,
) : LedgerRepo {

    private val logger = Logger.withTag("[K]LedgerRepo")

    override val list: Flow<ImmutableList<Ledger>>
        get() = ledgerManager.devices
            .map { devices ->
                devices.map { Ledger(Ledger.Id(it.id), it.product) }.toImmutableList()
            }
            .flowOn(Dispatchers.Default)

    override fun accounts(
        listener: LedgerListener,
        ledger: Ledger,
        paths: List<DerivationPath>,
    ): Flow<Either<LedgerFailure, Signer>> = flow<Either<LedgerFailure, Signer>> {
        either {
            val zeroDerivationPath = ledgerDerivationPath()

            ledgerManager.open(
                listener = LedgerListenerAdapter(listener),
                id = ledger.id.value,
            ).use { connection ->
                ensure(connection.getAppAndVersion().name == "Ethereum") {
                    LedgerFailure.NotEthereumApp()
                }

                val mainAddress = connection.ethereumAddress(
                    derivationPath = zeroDerivationPath.path,
                )

                val location = Location.Ledger(
                    mainAddress = Address(mainAddress).getOrNull()!!,
                    product = ledger.product,
                )

                suspend fun derive(
                    derivationPath: DerivationPath,
                ): Signer {
                    val address = connection.ethereumAddress(
                        derivationPath = derivationPath.path,
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
            .onLeft { emit(it.left()) }
    }
        .catchLedgerFailure()
        .flowOn(Dispatchers.Default)

    override suspend fun signPersonalMessage(
        listener: LedgerListener,
        ledger: Ledger,
        message: String,
        derivationPath: DerivationPath,
    ): Either<LedgerFailure, Signature> = withContext(Dispatchers.Default) {
        either {
            catch({
                doSignPersonalMessage(ledger, listener, message, derivationPath).right()
            }) {
                mapLedgerFailure(it).left()
            }.bind()
        }
    }

    private suspend fun Raise<LedgerFailure>.doSignPersonalMessage(
        ledger: Ledger,
        listener: LedgerListener,
        message: String,
        derivationPath: DerivationPath,
    ) = ledgerManager.open(
        id = ledger.id.value,
        listener = LedgerListenerAdapter(listener),
    ).use { connection ->
        ensure(connection.getAppAndVersion().name == "Ethereum") {
            LedgerFailure.NotEthereumApp()
        }

        val bytes = message.encodeUtf8()

        val signature = connection.signPersonalMessage(
            derivationPath = derivationPath.path,
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
        listener: LedgerListener,
        ledger: Ledger,
        typedMessage: String,
        derivationPath: DerivationPath,
    ): Either<LedgerFailure, Signature> = withContext(Dispatchers.Default) {
        either {
            catch({
                doSignTypedMessage(listener, ledger, typedMessage, derivationPath).right()
            }) {
                mapLedgerFailure(it).left()
            }.bind()
        }
    }

    private suspend fun Raise<LedgerFailure>.doSignTypedMessage(
        listener: LedgerListener,
        ledger: Ledger,
        typedMessage: String,
        derivationPath: DerivationPath,
    ): Signature = coroutineScope {
        val eip712 = Eip712V2.fromJson(typedMessage)
        val parameters = async { ledgerOffChain.getEip712Parameters(typedMessage, eip712) }

        ledgerManager.open(
            id = ledger.id.value,
            listener = LedgerListenerAdapter(listener),
        ).use { connection ->
            ensure(connection.getAppAndVersion().name == "Ethereum") {
                LedgerFailure.NotEthereumApp()
            }

            val signature = connection.signTypedMessage(
                derivationPath = derivationPath.path,
                eip712 = eip712,
                parameters = parameters.await(),
            )

            val messageHash = Wallet.typeDataHash(Eip712.fromJson(typedMessage))

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
    }

    override suspend fun signTransaction(
        listener: LedgerListener,
        ledger: Ledger,
        transaction: TransactionData,
        derivationPath: DerivationPath,
    ): Either<LedgerFailure, Signature> = withContext(Dispatchers.Default) {
        either {
            catch({
                doSignTransaction(listener, ledger, transaction, derivationPath).right()
            }) {
                mapLedgerFailure(it).left()
            }.bind()
        }
    }

    private suspend fun Raise<LedgerFailure>.doSignTransaction(
        listener: LedgerListener,
        ledger: Ledger,
        transaction: TransactionData,
        derivationPath: DerivationPath,
    ): Signature = coroutineScope {
        val transactionParameters = async {
            transaction.tx.input.bytes().takeIf { it.size >= 4 }?.let { input ->
                transaction.tx.to?.let { destination ->
                    ledgerOffChain.getTransactionParameters(
                        transaction.tx.chainId.value, Value.Address(destination.bytes()), input
                    )
                }
            }
        }

        ledgerManager.open(
            id = ledger.id.value,
            listener = LedgerListenerAdapter(listener),
        ).use { connection ->
            ensure(connection.getAppAndVersion().name == "Ethereum") {
                LedgerFailure.NotEthereumApp()
            }
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
                transaction = type1559.encode(),
                parameters = transactionParameters.await(),
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

    private fun <T> Flow<Either<LedgerFailure, T>>.catchLedgerFailure() = catch {
        emit(it.left().mapLedgerFailure())
    }

    private fun <T> Either<Throwable, T>.mapLedgerFailure() = mapLeft { mapLedgerFailure(it) }

    private fun mapLedgerFailure(e: Throwable): LedgerFailure {
        logger.w(e) { "Error happened" }
        return when (e) {
            is PermissionNotGrantedException -> LedgerFailure.PermissionNotGranted()
            is UsbInterfaceNotClaimedException -> LedgerFailure.UsbInterfaceNotClaimed()
            is RequestCanceledException -> LedgerFailure.RequestCanceled()
            else -> LedgerFailure.Other()
        }
    }
}