package kosh.domain.usecases.transaction.parser

import arrow.core.raise.either
import kosh.domain.entities.NetworkEntity
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
import kosh.eth.proposals.erc1155.Erc1155Abi
import kosh.eth.proposals.erc20.Erc20Abi
import kosh.eth.proposals.erc721.Erc721Abi

class TransferParser(
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
            Erc20Abi.transfer.selector -> {
                val (dest, value1) = Erc20Abi.transfer(input.bytes())

                ContractCall.Transfer(
                    chainId = chainId,
                    destination = Address(ByteString(dest.value)),
                    amounts = listOf(value1.value),
                    selector = FunSelector(ByteString(selector)),
                    from = from,
                    token = to,
                    data = ByteString(),
                    sender = from,
                    tokenIds = listOf()
                )
            }

            Erc20Abi.transferFrom.selector -> {
                val (src, dest, value1) = Erc20Abi.transferFrom(input.bytes())

                val token = getToken(chainId, to)

                when (token?.type) {
                    TokenMetadata.Type.ERC721 -> {
                        ContractCall.Transfer(
                            chainId = chainId,
                            destination = Address(ByteString(dest.value)),
                            amounts = listOf(BigInteger.ONE),
                            tokenIds = listOf(value1.value),
                            selector = FunSelector(ByteString(selector)),
                            from = Address(ByteString(src.value)),
                            token = to,
                            data = ByteString(),
                            sender = from,
                        )
                    }

                    else -> ContractCall.Transfer(
                        chainId = chainId,
                        destination = Address(ByteString(dest.value)),
                        amounts = listOf(value1.value),
                        selector = FunSelector(ByteString(selector)),
                        from = Address(ByteString(src.value)),
                        token = to,
                        data = ByteString(),
                        sender = from,
                        tokenIds = listOf()
                    )
                }
            }

            Erc721Abi.safeTransferFrom.selector -> {
                val (src, dest, tokenId) = Erc721Abi.safeTransferFrom(input.bytes())

                ContractCall.Transfer(
                    chainId = chainId,
                    destination = Address(ByteString(dest.value)),
                    amounts = listOf(BigInteger.ONE),
                    tokenIds = listOf(tokenId.value),
                    selector = FunSelector(ByteString(selector)),
                    from = Address(ByteString(src.value)),
                    token = to,
                    data = ByteString(),
                    sender = from,
                )
            }

            Erc721Abi.More.safeTransferFrom.selector -> {
                val (src, dest, tokenId, data) = Erc721Abi.More.safeTransferFrom(input.bytes())

                ContractCall.Transfer(
                    chainId = chainId,
                    destination = Address(ByteString(dest.value)),
                    amounts = listOf(BigInteger.ONE),
                    tokenIds = listOf(tokenId.value),
                    selector = FunSelector(ByteString(selector)),
                    from = Address(ByteString(src.value)),
                    token = to,
                    data = ByteString(data.value),
                    sender = from,
                )
            }

            Erc1155Abi.safeTransferFrom.selector -> {
                val (src, dest, tokenId, value1, data) = Erc1155Abi.safeTransferFrom(input.bytes())

                ContractCall.Transfer(
                    chainId = chainId,
                    destination = Address(ByteString(dest.value)),
                    amounts = listOf(value1.value),
                    tokenIds = listOf(tokenId.value),
                    selector = FunSelector(ByteString(selector)),
                    from = Address(ByteString(src.value)),
                    token = to,
                    data = ByteString(data.value),
                    sender = from,
                )
            }

            Erc1155Abi.safeBatchTransferFrom.selector -> {
                val (src, dest, tokenIds, values, data) = Erc1155Abi.safeBatchTransferFrom(input.bytes())

                ContractCall.Transfer(
                    chainId = chainId,
                    destination = Address(ByteString(dest.value)),
                    tokenIds = tokenIds.map { it.value },
                    amounts = values.map { it.value },
                    selector = FunSelector(ByteString(selector)),
                    from = Address(ByteString(src.value)),
                    token = to,
                    data = ByteString(data.value),
                    sender = from,
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
