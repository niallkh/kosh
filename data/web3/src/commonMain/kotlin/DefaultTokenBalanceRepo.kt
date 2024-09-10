package kosh.data.web3

import arrow.core.raise.either
import co.touchlab.kermit.Logger
import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.token.Balance
import kosh.domain.repositories.TokenBalanceRepo
import kosh.domain.serializers.Either
import kosh.domain.usecases.network.NetworkService
import kosh.eth.abi.abiAddress
import kosh.eth.proposals.ContractCall
import kosh.eth.proposals.at
import kosh.eth.proposals.erc1155.Erc1155Abi
import kosh.eth.proposals.erc20.Erc20Abi
import kosh.eth.proposals.erc721.Erc721Abi
import kosh.eth.proposals.multicall.MulticallAbi
import kosh.eth.proposals.multicall.multicall
import kosh.eth.rpc.Web3ProviderFactory
import kotlinx.datetime.Clock

class DefaultTokenBalanceRepo(
    private val web3ProviderFactory: Web3ProviderFactory,
    private val networkService: NetworkService,
) : TokenBalanceRepo {

    private val logger = Logger.withTag("[K]TokenBalanceRepo")

    override suspend fun getBalances(
        networkId: NetworkEntity.Id,
        account: Address,
        tokens: List<TokenEntity>,
    ): Either<Web3Failure, List<Balance>> = either {
        val now = Clock.System.now()

        val calls = tokens.map { token -> mapToCall(account, token) }

        val web3 = web3ProviderFactory(networkService.getRpc(networkId))

        web3.catch(logger) {
            web3.multicall(*calls.toTypedArray())
        }.bind()

        calls.map { call ->
            val balance = call.result.getOrNull() ?: BigInteger.ZERO

            Balance(
                value = balance,
                updatedAt = now,
            )
        }
    }

    private fun mapToCall(
        account: Address,
        token: TokenEntity,
    ): ContractCall<BigInteger> = when (token.type) {
        TokenEntity.Type.Native -> MulticallAbi
            .getEthBalance(account.bytes().abiAddress)
            .at(MulticallAbi.address)

        TokenEntity.Type.Erc20 -> Erc20Abi
            .balanceOf(account.bytes().abiAddress)
            .at(token.address!!.bytes().abiAddress)

        TokenEntity.Type.Erc721 -> Erc721Abi
            .balanceOf(account.bytes().abiAddress, token.tokenId!!)
            .at(token.address!!.bytes().abiAddress)

        TokenEntity.Type.Erc1155 -> Erc1155Abi
            .balanceOf(account.bytes().abiAddress, token.tokenId!!)
            .at(token.address!!.bytes().abiAddress)
    }
}
