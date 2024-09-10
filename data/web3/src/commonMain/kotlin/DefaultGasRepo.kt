package kosh.data.web3

import arrow.core.raise.either
import co.touchlab.kermit.Logger
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.web3.GasEstimation
import kosh.domain.models.web3.GasPrice
import kosh.domain.models.web3.GasPrices
import kosh.domain.repositories.GasRepo
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Either
import kosh.domain.usecases.network.GetRpcProvidersUC
import kosh.domain.usecases.network.NetworkService
import kosh.domain.usecases.network.invoke
import kosh.eth.abi.abiAddress
import kosh.eth.rpc.Web3Provider
import kosh.eth.rpc.Web3ProviderFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultGasRepo(
    private val web3ProviderFactory: Web3ProviderFactory,
    private val getRpcProvidersUC: GetRpcProvidersUC,
    private val networkService: NetworkService,
) : GasRepo {

    private val logger = Logger.withTag("[K]GasRepo")

    override suspend fun gasPrices(
        id: NetworkEntity.Id,
    ): Either<Web3Failure, GasPrices> = withContext(Dispatchers.Default) {
        either {
            val web3 = web3ProviderFactory(networkService.getRpc(id))

            val feeHistory = web3.catch(logger) {
                web3.feeHistory(
                    blockCount = 5u,
                    newestBlock = Web3Provider.BlockTag.Latest,
                    rewardPercentiles = listOf(5u, 30u, 80u)
                )
            }.bind()

            val full = feeHistory.gasUsedRatio.all { it > 0.5 }

            val base = feeHistory.baseFeePerGas.max().let {
                var extra = it
                extra = extra.add(it.multiply(BigInteger.ONE).div(BigInteger(8)))
                if (full) extra = extra.add(it.multiply(BigInteger.ONE).div(BigInteger(8)))
                extra
            }

            val slow = feeHistory.reward.map { it[0] }
                .reduce { a, p -> a + p } / feeHistory.reward.size
            val medium = feeHistory.reward.map { it[1] }
                .reduce { a, p -> a + p } / feeHistory.reward.size
            val fast = feeHistory.reward.map { it[2] }
                .reduce { a, p -> a + p } / feeHistory.reward.size

            GasPrices(
                current = GasPrice(
                    base = feeHistory.baseFeePerGas.max(),
                    priority = feeHistory.reward.last().first(),
                ),
                slow = GasPrice(
                    base = base,
                    priority = slow
                ),
                medium = GasPrice(
                    base = base,
                    priority = medium,
                ),
                fast = GasPrice(
                    base = base,
                    priority = fast,
                )
            )
        }
    }

    override suspend fun estimate(
        chainId: ChainId,
        from: Address,
        to: Address?,
        value: BigInteger,
        data: ByteString?,
        gas: ULong?,
    ): Either<Web3Failure, GasEstimation> = withContext(Dispatchers.Default) {
        either {
            val web3 = web3ProviderFactory(getRpcProvidersUC(chainId))

            val estimated = web3.catch(logger) {
                web3.estimateGas(
                    sender = from.bytes().abiAddress,
                    target = to?.bytes()?.abiAddress,
                    data = data?.bytes() ?: okio.ByteString.EMPTY,
                    value = value,
                    gas = gas?.toBigInteger(),
                )
            }.bind()

            GasEstimation(
                estimated = estimated,
                gas = gas ?: estimated,
            )
        }
    }
}
