package kosh.domain.usecases.transaction.parser

import arrow.core.raise.either
import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.FunSelector
import kosh.domain.models.funSelector
import kosh.domain.models.token.TokenMetadata
import kosh.domain.models.web3.ContractCall
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Either
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.erc20Token
import kosh.domain.state.tokens
import kosh.domain.utils.optic
import kosh.eth.abi.Value
import kosh.eth.abi.selector
import kosh.eth.proposals.erc20.Erc20Abi
import kosh.eth.proposals.erc721.Erc721Abi

class ApproveParser(
    private val appStateProvider: AppStateProvider,
) : ContractCallParser {

    override suspend fun parse(
        chainId: ChainId,
        from: Address,
        to: Address?,
        value: BigInteger,
        input: ByteString,
    ): Either<Web3Failure, ContractCall?> = either {
        to ?: return@either null
        if (!value.isZero()) return@either null

        when (val selector = input.funSelector()?.bytes()) {
            Erc20Abi.approve.selector!! -> {
                val (spender, value1) = Erc20Abi.approve(input.bytes())

                val token = getToken(chainId, to)

                when (token?.type) {
                    TokenMetadata.Type.ERC721 -> ContractCall.Approve(
                        chainId = chainId,
                        selector = FunSelector(ByteString(selector)),
                        owner = from,
                        spender = Address(ByteString(spender.value)),
                        tokenId = value1.value,
                        approved = BigInteger.ONE,
                        token = to,
                    )

                    else -> ContractCall.Approve(
                        chainId = chainId,
                        selector = FunSelector(ByteString(selector)),
                        owner = from,
                        spender = Address(ByteString(spender.value)),
                        tokenId = null,
                        approved = value1.value,
                        token = to,
                    )
                }
            }

            Erc721Abi.setApprovalForAll.selector!! -> {
                val (operator, approved) = Erc721Abi.setApprovalForAll(input.bytes())

                ContractCall.Approve(
                    chainId = chainId,
                    token = to,
                    selector = FunSelector(ByteString(selector)),
                    owner = from,
                    spender = Address(ByteString(operator.value)),
                    approved = if (approved.value) Value.BigNumber.UINT256_MAX else BigInteger.ZERO,
                    tokenId = null
                )
            }

            else -> null
        }
    }

    private fun getToken(
        chainId: ChainId,
        to: Address,
    ): TokenMetadata? {
        val networkId = NetworkEntity.Id(chainId)
        return appStateProvider.optic(AppState.erc20Token(networkId, to)).value
            ?.toTokenMetadata(chainId)
            ?: appStateProvider.optic(AppState.tokens).value.values
                .firstOrNull { it.networkId == networkId && it.address == to }
                ?.toTokenMetadata(chainId)
    }
}

fun TokenEntity.toTokenMetadata(chainId: ChainId) = TokenMetadata(
    type = when (type) {
        TokenEntity.Type.Native -> error("Invalid type")
        TokenEntity.Type.Erc20 -> TokenMetadata.Type.ERC20
        TokenEntity.Type.Erc721 -> TokenMetadata.Type.ERC721
        TokenEntity.Type.Erc1155 -> TokenMetadata.Type.ERC1155
    },
    chainId = chainId,
    address = address!!,
    name = name,
    symbol = symbol,
    decimals = decimals,
    icon = icon,
)

fun defaultTokenMetadata(
    chainId: ChainId,
    address: Address,
    type: TokenMetadata.Type = TokenMetadata.Type.ERC20,
) = TokenMetadata(
    type = type,
    chainId = chainId,
    address = address,
    name = "Unknown Token",
    symbol = "UNKWN",
    decimals = 0u,
    icon = null,
)
