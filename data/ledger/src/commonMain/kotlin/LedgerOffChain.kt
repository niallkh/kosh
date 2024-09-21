@file:OptIn(ExperimentalSerializationApi::class)

package kosh.data.trezor

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readAvailable
import kosh.eth.abi.Abi
import kosh.eth.abi.Value
import kosh.eth.abi.abiAddress
import kosh.eth.abi.at
import kosh.eth.abi.base.decodeBase64
import kosh.eth.abi.coder.decodeInputs
import kosh.eth.abi.eip712.Eip712
import kosh.eth.abi.functionSelector
import kosh.eth.abi.json.JsonEip712
import kosh.eth.abi.selector
import kosh.eth.proposals.erc1155.Erc1155Abi
import kosh.eth.proposals.erc20.Erc20Abi
import kosh.eth.proposals.erc721.Erc721Abi
import kosh.eth.wallet.fillSecureRandom
import kosh.libs.ledger.cmds.DomainInfo
import kosh.libs.ledger.cmds.Eip712Filters
import kosh.libs.ledger.cmds.Eip712Filters.Format
import kosh.libs.ledger.cmds.ExternalPluginInfo
import kosh.libs.ledger.cmds.NftInfo
import kosh.libs.ledger.cmds.PluginInfo
import kosh.libs.ledger.cmds.SignTransactionParameters
import kosh.libs.ledger.cmds.SignTypedMessageParameters
import kosh.libs.ledger.cmds.TokenInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.encodeToByteString
import kotlinx.io.bytestring.hexToByteString
import kotlinx.io.bytestring.toHexString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import kotlinx.io.readByteString
import kotlinx.io.readString
import kotlinx.io.write
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.io.decodeFromSource
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.kotlincrypto.hash.sha2.SHA224

private const val CRYPTO_ASSETS_API = "https://cdn.live.ledger.com/cryptoassets"
private const val PLUGINS_API = "https://cdn.live.ledger.com/plugins"
private const val NFT_API = "https://nft.api.live.ledger.com/v1"

class LedgerOffChain(
    private val client: HttpClient,
    private val json: Json = Json,
) {

    suspend fun getEip712Parameters(
        typedMessage: String,
        eip712: Eip712,
    ): SignTypedMessageParameters? {
        val chainId = eip712.domain.chainId?.value?.ulongValue() ?: return null

        val response = client.get("$CRYPTO_ASSETS_API/eip712_v2.json")
        val bufferedSource = if (response.status.isSuccess())
            response.bodyAsChannel().readBuffer()
        else
            return null

        val map = json.decodeFromSource<Map<String, Eip712Filters>>(bufferedSource)

        val filters = map[filterKey(typedMessage)]

        val coinRefAndAddresses = filters?.fields?.asSequence()
            ?.filter { it.format == Format.TOKEN }
            ?.distinctBy { it.coinRef }
            ?.map {
                if (it.coinRef!! == 255) {
                    it.coinRef!! to eip712.domain.verifyingContract!!
                } else {
                    it.coinRef!! to eip712.message.at(it.path.split(".")).abiAddress
                }
            }
            ?.sortedBy { it.first }
            ?.toList()
            ?: listOf()

        val tokenInfos = coinRefAndAddresses.takeIf { it.isNotEmpty() }?.let { addresses ->
            getTokens(chainId) { address ->
                addresses.any { (_, token) -> token == address }
            }?.let { tokenInfos ->
                addresses.map { (_, address) ->
                    tokenInfos.firstOrNull { it.address == address }
                }
            }
        }?.filterNotNull() ?: listOf()

        return SignTypedMessageParameters(
            filters = filters,
            tokens = tokenInfos
        )
    }

    suspend fun getTransactionParameters(
        chainId: ULong,
        address: Value.Address,
        input: ByteString,
    ): SignTransactionParameters? = coroutineScope {
        if (input.size < 4) return@coroutineScope null
        val selector = input.functionSelector()!!

        val addresses = listOfNotNull(
            address.takeIf {
                selector in arrayOf(
                    Erc20Abi.transfer.selector,
                    Erc20Abi.transferFrom.selector,
                    Erc20Abi.approve.selector
                )
            }
        ).toMutableList()

        val plugin = getPlugin(address, selector)

        if (plugin?.erc20OfInterest?.isNotEmpty() == true) {
            plugin.erc20OfInterest.forEach { path ->
                val value = plugin.abi.decodeInputs(input)
                    .at(*path.split(".").toTypedArray())
                addresses.add(value.abiAddress)
            }
        }

        val tokenInfos = getTokens(chainId) { it in addresses }?.let { tokens ->
            addresses.map { address ->
                tokens.firstOrNull { token -> token.address == address }
            }.mapNotNull { it }
        } ?: listOf()

        val nft = async { getNft(chainId, address) }

        val domain = async { reverseResolve(address) }

        SignTransactionParameters(
            plugins = listOfNotNull(
                getNftPlugin(chainId, address, selector)?.let { PluginInfo(it) },
            ),
            externalPlugins = listOfNotNull(
                plugin?.let {
                    ExternalPluginInfo(
                        name = plugin.name,
                        data = plugin.data,
                        signature = plugin.signature,
                    )
                }
            ),
            nfts = listOfNotNull(nft.await()),
            tokens = tokenInfos,
            domains = listOfNotNull(domain.await())
        )
    }

    private suspend fun getPlugin(
        address: Value.Address,
        selector: ByteString,
    ): ExternalPlugin? {
        val response =
            client.get("$PLUGINS_API/ethereum.json")
        val jsonElement = if (response.status.isSuccess()) {
            json.decodeFromSource<JsonElement>(response.bodyAsChannel().readBuffer())
        } else null

        val plugin = jsonElement?.jsonObject?.get("0x${address.value.toHexString()}")
            ?.jsonObject?.get("0x${selector.toHexString()}")
            ?.jsonObject
            ?: return null

        val erc20OfInterest = plugin.get("erc20OfInterest")?.jsonArray?.map {
            it.jsonPrimitive.content
        } ?: listOf()

        val abi = jsonElement.jsonObject.get("0x${address.value.toHexString()}")
            ?.jsonObject?.get("abi")!!
            .let { Abi.from(it) }

        val item = abi.items.first { it.selector == selector } as Abi.Item.Function

        return ExternalPlugin(
            name = plugin.get("plugin")!!.jsonPrimitive.content,
            data = plugin.get("serialized_data")!!.jsonPrimitive.content.hexToByteString(),
            signature = plugin.get("signature")!!.jsonPrimitive.content.hexToByteString(),
            abi = item,
            erc20OfInterest = erc20OfInterest,
        )
    }

    private data class ExternalPlugin(
        val name: String,
        val abi: Abi.Item.Function,
        val erc20OfInterest: List<String>,
        val data: ByteString,
        val signature: ByteString,
    )

    private suspend fun getTokens(
        chainId: ULong,
        predicate: (Value.Address) -> Boolean,
    ): List<TokenInfo>? {
        val response = client.get("$CRYPTO_ASSETS_API/evm/") {
            url {
                appendPathSegments(
                    chainId.toString(),
                    "erc20-signatures.json"
                )
            }
        }
        val buffer = if (response.status.isSuccess()) {
            json.decodeFromSource<String>(response.bodyAsChannel().readBuffer())
                .decodeBase64().let { Buffer().apply { write(it) } }
        } else
            return null

        return buildList {
            while (!buffer.exhausted()) {
                val data = buffer.readByteString(buffer.readInt())

                Buffer().apply { write(data) }.run {
                    val ticker = readString(readByte().toUByte().toLong())
                    val address = Value.Address(readByteString(20))
                    val decimals = readInt().toUInt()
                    val chainId1 = readInt().toUInt()
                    val signature = readByteString()

                    if (predicate(address)) {
                        add(
                            TokenInfo(
                                ticker = ticker,
                                address = address,
                                decimals = decimals,
                                chainId = chainId1,
                                signature = signature,
                                data = data,
                            )
                        )
                    }
                }
            }
        }
    }

    private suspend fun reverseResolve(
        address: Value.Address,
    ): DomainInfo? {
        val response = client.get("$NFT_API/ethereum/") {
            url {
                appendPathSegments(
                    "names",
                    "ens",
                    "reverse",
                    "0x${address.value.toHexString()}",
                )

                val challenge = UnsafeByteStringOperations
                    .wrapUnsafe(ByteArray(4).fillSecureRandom())
                parameter("challenge", challenge.toHexString())
            }
        }

        val jsonElement = if (response.status.isSuccess())
            json.decodeFromSource<JsonElement>(response.bodyAsChannel().readBuffer())
        else
            return null

        return jsonElement.jsonObject["payload"]?.jsonPrimitive?.content
            ?.removePrefix("0x")
            ?.hexToByteString()
            ?.let { DomainInfo(it) }
    }

    private suspend fun getNft(
        chainId: ULong,
        contractAddress: Value.Address,
    ): NftInfo? {
        val response = client.get("$NFT_API/ethereum/") {
            url {
                appendPathSegments(
                    chainId.toString(),
                    "contracts",
                    "0x${contractAddress.value.toHexString()}"
                )
            }
        }
        val jsonElement = if (response.status.isSuccess())
            json.decodeFromSource<JsonElement>(response.bodyAsChannel().readBuffer())
        else
            return null

        return jsonElement.jsonObject["payload"]?.jsonPrimitive?.content
            ?.removePrefix("0x")
            ?.hexToByteString()
            ?.let { NftInfo(it) }
    }

    private suspend fun getNftPlugin(
        chainId: ULong,
        contractAddress: Value.Address,
        selector: ByteString,
    ): ByteString? {
        when (selector) {
            Erc721Abi.approve.selector,
            Erc721Abi.setApprovalForAll.selector,
            Erc721Abi.transferFrom.selector,
            Erc721Abi.safeTransferFrom.selector,
            Erc721Abi.More.safeTransferFrom.selector,
            Erc1155Abi.safeTransferFrom.selector,
            Erc1155Abi.safeBatchTransferFrom.selector,
            -> Unit

            else -> return null
        }

        val response = client.get("$NFT_API/ethereum/") {
            url {
                appendPathSegments(
                    chainId.toString(),
                    "contracts",
                    "0x${contractAddress.value.toHexString()}",
                    "plugin-selector",
                    "0x${selector.toHexString()}"
                )
            }
        }
        val jsonElement = if (response.status.isSuccess())
            json.decodeFromSource<JsonElement>(response.bodyAsChannel().readBuffer())
        else
            return null

        return jsonElement.jsonObject["payload"]?.jsonPrimitive?.content
            ?.removePrefix("0x")
            ?.hexToByteString()
    }

    private fun filterKey(typedMessage: String): String {
        val jsonEip712 = JsonEip712.from(typedMessage)
        val types = jsonEip712.types.entries.sortedBy { it.key }.associate { it.toPair() }
        val schemaHash = json.encodeToString(types).encodeToByteString().sha224()
        return "${jsonEip712.domain.chainId}:${jsonEip712.domain.verifyingContract}:${schemaHash.toHexString()}"
    }

    private fun ByteString.sha224(): ByteString {
        lateinit var result: ByteString
        UnsafeByteStringOperations.withByteArrayUnsafe(this) {
            result = UnsafeByteStringOperations.wrapUnsafe(SHA224().digest(it))
        }
        return result
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
