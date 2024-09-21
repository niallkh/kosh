package kosh.data.web3

import arrow.core.raise.either
import co.touchlab.kermit.Logger
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.Hash
import kosh.domain.models.web3.GasPrice
import kosh.domain.models.web3.Log
import kosh.domain.models.web3.Receipt
import kosh.domain.models.web3.ReceiptLogs
import kosh.domain.models.web3.Signature
import kosh.domain.repositories.TransactionRepo
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Either
import kosh.domain.usecases.network.GetRpcProvidersUC
import kosh.domain.usecases.network.NetworkService
import kosh.domain.usecases.network.invoke
import kosh.eth.abi.abi
import kosh.eth.abi.address
import kosh.eth.abi.keccak256
import kosh.eth.rpc.Web3ProviderFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant

class DefaultTransactionRepo(
    private val web3ProviderFactory: Web3ProviderFactory,
    private val getRpcProvidersUC: GetRpcProvidersUC,
    private val networkService: NetworkService,
) : TransactionRepo {

    private val logger = Logger.withTag("[K]TransactionRepo")

    override suspend fun nextNonce(
        chainId: ChainId,
        address: Address,
    ): Either<Web3Failure, ULong> = withContext(Dispatchers.Default) {
        either {
            val web3 = web3ProviderFactory(getRpcProvidersUC(chainId))

            web3.getNonce(address = address.bytes().abi.address)
        }
    }

    override suspend fun send(
        chainId: ChainId,
        transaction: Signature,
    ): Either<Web3Failure, Hash> = withContext(Dispatchers.Default) {
        either {
            val web3 = web3ProviderFactory(getRpcProvidersUC(chainId, write = true))
            val hash = web3.sendRawTransaction(transaction.data.bytes())
            check(hash.bytes == transaction.data.bytes().keccak256())
            Hash(ByteString(transaction.data.bytes().keccak256()))
        }
    }

    override suspend fun receipt(
        networkId: NetworkEntity.Id,
        hash: Hash,
    ): Either<Web3Failure, ReceiptLogs?> = withContext(Dispatchers.Default) {
        either {
            val web3 = web3ProviderFactory(networkService.getRpc(networkId))

            val receipt = web3.catch(logger) {
                getTransactionReceipt(kosh.eth.rpc.Hash(hash.value.bytes()))
            }.bind()

            if (receipt == null) return@either null

            val block = web3.catch(logger) {
                getBlockByHash(receipt.blockHash)
            }.bind()

            val baseFee = block.baseFeePerGas ?: BigInteger.ZERO
            val priorityFee = receipt.effectiveGasPrice - baseFee

            ReceiptLogs(
                receipt = Receipt(
                    transactionHash = Hash(ByteString(receipt.transactionHash.bytes)),
                    success = receipt.status == BigInteger.ONE,
                    blockHash = Hash(ByteString(receipt.blockHash.bytes)),
                    gasUsed = receipt.gasUsed.ulongValue(),
                    gasPrice = GasPrice(
                        base = baseFee,
                        priority = priorityFee,
                    ),
                    time = Instant.fromEpochSeconds(block.timestamp.longValue()),
                ),
                logs = receipt.logs.map { ethLog ->
                    Log(
                        address = Address(ByteString(ethLog.address.value)),
                        data = ByteString(ethLog.data),
                        topics = ethLog.topics.map { ByteString(it) }
                    )
                },
            )
        }
    }
}
