package kosh.domain.usecases.transaction

import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.withError
import arrow.optics.dsl.at
import arrow.optics.typeclasses.At
import co.touchlab.kermit.Logger
import kosh.domain.entities.TransactionEntity
import kosh.domain.entities.eip1559
import kosh.domain.entities.gasPrice
import kosh.domain.entities.logs
import kosh.domain.entities.modifiedAt
import kosh.domain.entities.receipt
import kosh.domain.failure.TransactionFailure
import kosh.domain.models.ByteString
import kosh.domain.models.Hash
import kosh.domain.models.wc.DappMetadata
import kosh.domain.models.web3.GasPrice
import kosh.domain.models.web3.Log
import kosh.domain.models.web3.Signature
import kosh.domain.models.web3.TransactionData
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.FilesRepo
import kosh.domain.repositories.TransactionRepo
import kosh.domain.repositories.modify
import kosh.domain.repositories.optic
import kosh.domain.serializers.Either
import kosh.domain.state.AppState
import kosh.domain.state.account
import kosh.domain.state.network
import kosh.domain.state.optionalTransaction
import kosh.domain.state.transaction
import kosh.domain.state.transactions
import kosh.domain.utils.pmap
import kotlinx.datetime.Clock
import kotlinx.serialization.builtins.ListSerializer

class Eip1559TransactionService(
    private val appStateRepo: AppStateRepo,
    private val transactionRepo: TransactionRepo,
    private val fileRepo: FilesRepo,
) {
    private val logger = Logger.withTag("[K]TransactionService")

    suspend fun send(
        transaction: TransactionData,
        signature: Signature,
        dapp: DappMetadata,
    ): Either<TransactionFailure, Hash> = either {
        logger.v { "send(transaction=$transaction)" }

        val hash = withError(TransactionFailure::Connection) {
            transactionRepo.send(transaction.tx.chainId, signature).bind()
        }

        appStateRepo.modify {
            val network = AppState.network(transaction.tx.chainId).get()
                ?: raise(TransactionFailure.InvalidTransaction())

            val account = AppState.account(transaction.tx.from).get()
                ?: raise(TransactionFailure.InvalidTransaction())

            val eip1559 = TransactionEntity.Eip1559(
                networkId = network.id,
                target = transaction.tx.to,
                sender = account.id,
                hash = hash,
                value = transaction.tx.value,
                nonce = transaction.nonce,
                data = fileRepo.write(transaction.tx.input),
                gasPrice = transaction.gasPrice,
                gasLimit = transaction.gasLimit,
                dapp = TransactionEntity.Dapp(
                    name = dapp.name,
                    url = dapp.url,
                    icon = dapp.icon
                ),
            )

            ensure(eip1559.id !in AppState.transactions.get()) {
                TransactionFailure.AlreadyExist()
            }

            AppState.transactions.at(At.pmap(), eip1559.id) set eip1559
        }

        hash
    }

    suspend fun speedUp(
        id: TransactionEntity.Id,
        gasPrice: GasPrice,
        signature: Signature,
    ): Either<TransactionFailure, ByteString> = either {
        val transaction = appStateRepo.optic(AppState.transaction(id)).value
            ?: raise(TransactionFailure.NotFound())
        transaction as? TransactionEntity.Eip1559
            ?: raise(TransactionFailure.InvalidTransaction())
        val network = appStateRepo.optic(AppState.network(transaction.networkId)).value
            ?: raise(TransactionFailure.NotFound())

        ensure(gasPrice.base >= transaction.gasPrice.base) {
            TransactionFailure.Underpriced()
        }
        ensure(gasPrice.priority >= transaction.gasPrice.priority) {
            TransactionFailure.Underpriced()
        }

        val hash = withError(TransactionFailure::Connection) {
            transactionRepo.send(network.chainId, signature).bind()
        }

        appStateRepo.modify {
            inside(AppState.optionalTransaction(id)) {
                TransactionEntity.eip1559.gasPrice set gasPrice
            }
        }

        ByteString(hash.value.bytes())
    }

    suspend fun finalize(
        id: TransactionEntity.Id,
    ): Either<TransactionFailure, Unit> = either {
        val transaction = appStateRepo.optic(AppState.transaction(id)).value
            ?: raise(TransactionFailure.NotFound())

        transaction as? TransactionEntity.Eip1559
            ?: raise(TransactionFailure.InvalidTransaction())

        val receiptLogs = withError(TransactionFailure::Connection) {
            transactionRepo.receipt(
                networkId = transaction.networkId,
                hash = transaction.hash
            ).bind()
        } ?: raise(TransactionFailure.ReceiptNotAvailable())

        val logsPath = fileRepo.put(receiptLogs.logs, ListSerializer(Log.serializer()))

        appStateRepo.modify {
            inside(AppState.optionalTransaction(id)) {
                TransactionEntity.eip1559.receipt set receiptLogs.receipt
                TransactionEntity.eip1559.logs set logsPath
                TransactionEntity.eip1559.modifiedAt set Clock.System.now()
            }
        }
    }
}
