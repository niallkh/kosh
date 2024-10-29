@file:OptIn(ExperimentalSerializationApi::class, ExperimentalStdlibApi::class)

package kosh.data.web3

import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.nullable
import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.CacheControl
import io.ktor.http.HttpHeaders
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readAvailable
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.models.ethereum
import kosh.domain.models.toLibUri
import kosh.domain.models.token.NftExtendedMetadata
import kosh.domain.models.token.NftMetadata
import kosh.domain.models.token.TokenMetadata
import kosh.domain.models.token.TokenMetadata.Type
import kosh.domain.repositories.TokenRepo
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Either
import kosh.domain.usecases.network.GetRpcProvidersUC
import kosh.domain.usecases.network.invoke
import kosh.eth.abi.abi
import kosh.eth.abi.address
import kosh.eth.proposals.at
import kosh.eth.proposals.erc1155.Erc1155Abi
import kosh.eth.proposals.erc165.Erc165Abi
import kosh.eth.proposals.erc20.Erc20Abi
import kosh.eth.proposals.erc721.Erc721Abi
import kosh.eth.proposals.multicall.multicall
import kosh.eth.rpc.Web3ProviderFactory
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.bytestring.hexToByteString
import kotlinx.io.bytestring.toHexString
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.io.decodeFromSource

class DefaultTokenRepo(
    private val web3ProviderFactory: Web3ProviderFactory,
    private val getRpcProvidersUC: GetRpcProvidersUC,
    httpClient: HttpClient,
    private val json: Json = Json,
) : TokenRepo {

    private val httpClient = httpClient.config {
        expectSuccess = true
    }

    private val logger = Logger.withTag("TokenRepo")

    override suspend fun getTokenMetadata(
        chainId: ChainId,
        address: Address,
    ): Either<Web3Failure, TokenMetadata?> = withContext(Dispatchers.Default) {
        either {
            nullable {
                val web3Provider = web3ProviderFactory(getRpcProvidersUC(chainId).toLibUri())

                val abiAddress = address.bytes().abi.address
                val isErc165 =
                    Erc165Abi.supportsInterface(Erc165Abi.Erc165InterfaceId).at(abiAddress)
                val isInvalidErc165 =
                    Erc165Abi.supportsInterface(Erc165Abi.Erc165InvalidInterfaceId).at(abiAddress)
                val isErc20 = Erc165Abi.supportsInterface(Erc165Abi.Erc20InterfaceId).at(abiAddress)
                val isErc721 =
                    Erc165Abi.supportsInterface(Erc165Abi.Erc721InterfaceId).at(abiAddress)
                val isErc1155 =
                    Erc165Abi.supportsInterface(Erc165Abi.Erc1155InterfaceId).at(abiAddress)
                val nameCall = Erc20Abi.name().at(abiAddress)
                val symbolCall = Erc20Abi.symbol().at(abiAddress)
                val decimalsCall = Erc20Abi.decimals().at(abiAddress)

                web3Provider.catch(logger) {
                    web3Provider.multicall(
                        isErc165,
                        isInvalidErc165,
                        isErc20,
                        isErc721,
                        isErc1155,
                        nameCall,
                        symbolCall,
                        decimalsCall,
                    )
                }.bind()

                val name = nameCall.result.getOrElse {
                    logger.i(it) { "Invalid token name" }
                    raise(null)
                }

                val symbol = symbolCall.result.getOrElse { null }

                val decimals = decimalsCall.result.getOrElse { 0u }

                val erc155 = isErc1155.result.getOrNull() == true
                        && isInvalidErc165.result.getOrNull() == false

                val type = when {
                    erc155 && isErc20.result.getOrElse { false } -> Type.ERC20
                    erc155 && isErc721.result.getOrElse { false } -> Type.ERC721
                    erc155 && isErc1155.result.getOrElse { false } -> Type.ERC1155
                    else -> if (decimals == 0u) Type.ERC721 else Type.ERC20
                }

                TokenMetadata(
                    chainId = chainId,
                    address = address,
                    name = name,
                    symbol = symbol,
                    decimals = decimals.toUByte(),
                    type = type,
                    icon = null,
                )
            }
        }
    }

    override suspend fun getNftMetadata(
        chainId: ChainId,
        address: Address,
        tokenId: BigInteger,
        type: Type,
        refresh: Boolean,
    ): Either<Web3Failure, NftMetadata?> = withContext(Dispatchers.Default) {
        either {
            nullable {
                val web3Provider = web3ProviderFactory(getRpcProvidersUC(chainId).toLibUri())

                val tokenUriCall = when (type) {
                    Type.ERC20 -> error("Erc20 doesn't have nft metadata")
                    Type.ERC721 -> Erc721Abi.tokenUri(tokenId)
                    Type.ERC1155 -> Erc1155Abi.uri(tokenId)
                }.at(address.bytes().abi.address)

                web3Provider.catch(logger) {
                    web3Provider.multicall(tokenUriCall)
                }.bind()

                val tokenUri = tokenUriCall.result.getOrElse {
                    logger.i(it) { "Invalid token uri" }
                    raise(null)
                }.let {
                    if (
                        chainId == ethereum &&
                        address.bytes() == "D4416b13d2b3a9aBae7AcD5D6C2BbDBE25686401".hexToByteString()
                    ) {
                        "https://ens-metadata-service.appspot.com/mainnet/" +
                                "0x${address.bytes().toHexString()}/" +
                                "0x${tokenId.toString(16)}"
                    } else {
                        it
                    }
                }

                val metadata = getMetadata(
                    tokenUri = tokenUri,
                    refresh = refresh,
                    tokenId = tokenId,
                )

                val image = metadata.image ?: raise(null)

                NftMetadata(
                    chainId = chainId,
                    address = address,
                    tokenId = tokenId,
                    uri = Uri(tokenUri),
                    name = metadata.name,
                    image = Uri(image),
                    decimals = metadata.decimals,
                    symbol = metadata.symbol,
                    external = metadata.externalUrl?.let(Uri::invoke),
                    description = metadata.description,
                )
            }
        }
    }

    override suspend fun getNftMetadata(
        uri: Uri,
        tokenId: BigInteger,
        refresh: Boolean,
    ): Either<Web3Failure, NftExtendedMetadata?> = withContext(Dispatchers.Default) {
        either {
            val metadata = getMetadata(uri.toString(), tokenId, refresh)

            NftExtendedMetadata(
                description = metadata.description,
                attributes = metadata.attributes.mapNotNull {
                    nullable {
                        NftExtendedMetadata.Attribute(
                            traitType = it.traitType,
                            displayType = it.displayType,
                            value = ensureNotNull(it.value as? JsonPrimitive).content,
                        )
                    }
                }.toImmutableList()
            )
        }
    }

    private suspend fun Raise<Web3Failure>.getMetadata(
        tokenUri: String,
        tokenId: BigInteger,
        refresh: Boolean,
    ): Metadata {

        val response = httpClient.catch(logger) {
            get(
                tokenUri
                    .replace("0x{id}", "0x${tokenId.toString(16)}")
                    .replace("{id}", tokenId.toString(10))
            ) {
                if (refresh) {
                    headers {
                        append(HttpHeaders.CacheControl, CacheControl.NoCache(null).toString())
                    }
                }
            }
        }.bind()

        return json.decodeFromSource<Metadata>(response.bodyAsChannel().readBuffer())
    }
}

internal suspend fun ByteReadChannel.readBuffer(): Source {
    val buffer = Buffer()
    val buff = ByteArray(4096)
    while (!isClosedForRead) {
        val read = readAvailable(buff)
        if (read == -1) continue
        buffer.write(buff, 0, read)
    }
    return buffer
}
