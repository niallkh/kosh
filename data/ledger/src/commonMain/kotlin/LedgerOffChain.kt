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
import kosh.eth.abi.Eip712V2
import kosh.eth.abi.Value
import kosh.eth.abi.asAddress
import kosh.eth.abi.coder.decodeInputs
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
import kosh.libs.ledger.cmds.valueAt
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.okio.decodeFromBufferedSource
import okio.Buffer
import okio.BufferedSource
import okio.ByteString
import okio.ByteString.Companion.decodeBase64
import okio.ByteString.Companion.decodeHex
import okio.ByteString.Companion.encodeUtf8
import okio.ByteString.Companion.toByteString
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
        eip712: Eip712V2,
    ): SignTypedMessageParameters? {
        val chainId = eip712.domain.chainId?.value?.ulongValue() ?: return null

        val response = client.get("$CRYPTO_ASSETS_API/eip712_v2.json")
        val bufferedSource = if (response.status.isSuccess())
            response.bodyAsChannel().readBuffer()
        else
            return null

        val map = json.decodeFromBufferedSource<Map<String, Eip712Filters>>(bufferedSource)

        val filters = map[filterKey(typedMessage)]

        val coinRefAndAddresses = filters?.fields?.asSequence()
            ?.filter { it.format == Format.TOKEN }
            ?.distinctBy { it.coinRef }
            ?.map {
                if (it.coinRef!! == 255) {
                    it.coinRef!! to eip712.domain.verifyingContract!!
                } else {
                    it.coinRef!! to eip712.types.getValue(eip712.primaryType)
                        .valueAt(eip712.message, it.path.split("."))
                        .asAddress
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
        val selector = input.selector()!!

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
                val pathArray = path.split(".").toTypedArray()
                addresses.add(plugin.abi.decodeInputs(input, *pathArray) as Value.Address)
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
            json.decodeFromBufferedSource<JsonElement>(response.bodyAsChannel().readBuffer())
        } else null

        val plugin = jsonElement?.jsonObject?.get("0x${address.value.hex()}")
            ?.jsonObject?.get("0x${selector.hex()}")
            ?.jsonObject
            ?: return null

        val erc20OfInterest = plugin.get("erc20OfInterest")?.jsonArray?.map {
            it.jsonPrimitive.content
        } ?: listOf()

        val abi = jsonElement.jsonObject.get("0x${address.value.hex()}")
            ?.jsonObject?.get("abi")!!
            .let { Abi.from(it) }

        val item = abi.items.first { it.selector == selector } as Abi.Item.Function

        return ExternalPlugin(
            name = plugin.get("plugin")!!.jsonPrimitive.content,
            data = plugin.get("serialized_data")!!.jsonPrimitive.content.decodeHex(),
            signature = plugin.get("signature")!!.jsonPrimitive.content.decodeHex(),
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
            json.decodeFromBufferedSource<String>(response.bodyAsChannel().readBuffer())
                .decodeBase64()!!.let { Buffer().apply { write(it) } }
        } else
            return null

        return buildList {
            while (!buffer.exhausted()) {
                val data = buffer.readByteString(buffer.readInt().toUInt().toLong())

                Buffer().apply { write(data) }.run {
                    val ticker = readUtf8(readByte().toUByte().toLong())
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
                    "0x${address.value.hex()}",
                )

                parameter("challenge", ByteArray(4).fillSecureRandom().toByteString().hex())
            }
        }

        val jsonElement = if (response.status.isSuccess())
            json.decodeFromBufferedSource<JsonElement>(response.bodyAsChannel().readBuffer())
        else
            return null

        return jsonElement.jsonObject["payload"]?.jsonPrimitive?.content
            ?.removePrefix("0x")
            ?.decodeHex()
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
                    "0x${contractAddress.value.hex()}"
                )
            }
        }
        val jsonElement = if (response.status.isSuccess())
            json.decodeFromBufferedSource<JsonElement>(response.bodyAsChannel().readBuffer())
        else
            return null

        return jsonElement.jsonObject["payload"]?.jsonPrimitive?.content
            ?.removePrefix("0x")
            ?.decodeHex()
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
            Erc1155Abi.setApprovalForAll.selector,
            -> Unit

            else -> return null
        }

        val response = client.get("$NFT_API/ethereum/") {
            url {
                appendPathSegments(
                    chainId.toString(),
                    "contracts",
                    "0x${contractAddress.value.hex()}",
                    "plugin-selector",
                    "0x${selector.hex()}"
                )
            }
        }
        val jsonElement = if (response.status.isSuccess())
            json.decodeFromBufferedSource<JsonElement>(response.bodyAsChannel().readBuffer())
        else
            return null

        return jsonElement.jsonObject["payload"]?.jsonPrimitive?.content
            ?.removePrefix("0x")
            ?.decodeHex()
    }

    private fun filterKey(typedMessage: String): String {
        val jsonEip712 = JsonEip712.from(typedMessage)
        val types = jsonEip712.types.entries.sortedBy { it.key }.associate { it.toPair() }
        val schemaHash = json.encodeToString(types).encodeUtf8().sha224()
        return "${jsonEip712.domain.chainId}:${jsonEip712.domain.verifyingContract}:${schemaHash.hex()}"
    }

    private fun ByteString.sha224(): ByteString =
        SHA224().digest(toByteArray()).toByteString()
}


internal suspend fun ByteReadChannel.readBuffer(): BufferedSource {
    val buffer = Buffer()
    val buff = ByteArray(8192)
    while (!isClosedForRead) {
        val read = readAvailable(buff)
        if (read == -1) continue
        buffer.write(buff, 0, read)
    }
    return buffer
}
