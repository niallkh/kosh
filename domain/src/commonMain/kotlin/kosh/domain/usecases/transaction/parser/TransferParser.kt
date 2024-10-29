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
import kosh.eth.abi.selector
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
            Erc20Abi.transfer.selector!! -> {
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

            Erc20Abi.transferFrom.selector!! -> {
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

            Erc721Abi.safeTransferFrom.selector!! -> {
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

            Erc721Abi.More.safeTransferFrom.selector!! -> {
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

            Erc1155Abi.safeTransferFrom.selector!! -> {
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

            Erc1155Abi.safeBatchTransferFrom.selector!! -> {
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

// 2024-10-29 15:29:20.331 20889-22223 [K]AndroidReownAdapter  eth.kosh.app.debug                   V  onSessionRequest: SessionRequest(topic=6aed72c5441c2e4bd9eeebf58f0b2883060a05d14497479ce865999f59dbbd0c, chainId=eip155:1, peerMetaData=AppMetaData(name=ENS, description=ENS, url=https://app.ens.domains/niallkh.eth?tab=ownership, icons=[], redirect=, appLink=null, linkMode=false, verifyUrl=null), request=JSONRPCRequest(id=1730212159942699, method=eth_sendTransaction, params=[{"data":"","from":"0xAB35439B3D35f39D1389B3E69dAC0396D241B547","gas":"0x9262","maxFeePerGas":"0x3e84d9b73","maxPriorityFeePerGas":"0x10dae9","nonce":"0xd","to":"0xD4416b13d2b3a9aBae7AcD5D6C2BbDBE25686401"}])), VerifyContext(id=1730212159942699, origin=https://app.ens.domains, validation=VALID, verifyUrl=https://verify.walletconnect.org/, isScam=false)

// 0xf242432a
// 000000000000000000000000ab35439b3d35f39d1389b3e69dac0396d241b547
// 0000000000000000000000005eddc8a7669274a1506828bb6389a4c20feeec49
// b953a1ce74e848629afe8d2c9e793410f737ef08e01693c0789a29aae645c533
// 0000000000000000000000000000000000000000000000000000000000000001
// 00000000000000000000000000000000000000000000000000000000000000a0
// 0000000000000000000000000000000000000000000000000000000000000000
