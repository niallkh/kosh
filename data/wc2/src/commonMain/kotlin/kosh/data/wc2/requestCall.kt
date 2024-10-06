package kosh.data.wc2

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import arrow.core.raise.withError
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kosh.domain.failure.WcFailure
import kosh.domain.failure.WcFailure.WcInvalidRequest
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.models.wc.WcRequest
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.JsonTypeData
import kosh.domain.serializers.BigInteger
import kosh.eth.abi.json.JsonEip712
import kosh.libs.reown.AddEthereumNetwork
import kosh.libs.reown.SendTransaction
import kosh.libs.reown.WatchAsset
import kotlinx.io.bytestring.decodeToString
import kotlinx.io.bytestring.hexToByteString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
}

internal fun requestCall(
    requestChainId: ULong?,
    method: String,
    params: String,
): Either<WcFailure, WcRequest.Call> = either {
    when (method) {
        "personal_sign" -> {
            val decodeParams = json.decodeFromString<List<String>>(params)
            val message =
                decodeParams[0].removePrefix("0x").hexToByteString().decodeToString()
            val address = parseAddress(decodeParams[1])

            WcRequest.Call.SignPersonal(
                message = EthMessage(message),
                account = address,
            )
        }

        "eth_signTypedData_v4" -> {
            val decodeParams = json.decodeFromString<List<JsonElement>>(params)
            val address = parseAddress(decodeParams[0].jsonPrimitive.content)
            val json = json.encodeToString(decodeParams[1].jsonObject)
            val domain = JsonEip712.from(json).domain

            WcRequest.Call.SignTyped(
                chainId = domain.chainId?.let { ChainId(it) },
                json = JsonTypeData(json),
                account = address,
            )
        }

        "wallet_addEthereumChain" -> {
            val addNetwork = json.decodeFromString<List<AddEthereumNetwork>>(params).first()

            WcRequest.Call.AddNetwork(
                chainId = ChainId(addNetwork.chainId.parseHexNumber().ulongValue()),
                chainName = ensureNotNull(addNetwork.chainName) {
                    WcInvalidRequest.Other("Missing network name")
                },
                tokenName = ensureNotNull(addNetwork.nativeCurrency?.name) {
                    WcInvalidRequest.Other("Missing token name")
                },
                tokenSymbol = ensureNotNull(addNetwork.nativeCurrency?.symbol) {
                    WcInvalidRequest.Other("Missing token symbol")
                },
                tokenDecimals = ensureNotNull(addNetwork.nativeCurrency?.decimals?.toUByte()) {
                    WcInvalidRequest.Other("Missing token decimals")
                },
                rpcProviders = ensureNotNull(
                    addNetwork.rpcUrls?.map { Uri(it) }?.takeIf { it.isNotEmpty() }
                ) {
                    WcInvalidRequest.Other("Missing rpc provider")
                },
                explorers = addNetwork.blockExplorerUrls?.map { Uri(it) } ?: listOf(),
                icons = addNetwork.iconUrls?.map { Uri(it) } ?: listOf(),
            )
        }

        "wallet_watchAsset" -> {
            val watchAsset = json.decodeFromString<WatchAsset>(params)
            ensureNotNull(requestChainId) {
                WcInvalidRequest.Other("Chain Id expected")
            }

            WcRequest.Call.WatchAsset(
                chainId = requestChainId.let(ChainId::invoke),
                address = parseAddress(watchAsset.options.address),
                tokenId = watchAsset.options.tokenId?.let(BigInteger::parseString),
                icon = watchAsset.options.image?.let(Uri::invoke),
            )
        }

        "eth_sendTransaction" -> {
            val transaction = json.decodeFromString<List<SendTransaction>>(params)[0]
            val from = parseAddress(transaction.from)

            WcRequest.Call.SendTransaction(
                chainId = transaction.chainId
                    ?.parseHexNumber()?.ulongValue()?.let(ChainId::invoke)
                    ?: requestChainId?.let(ChainId::invoke)
                    ?: error("Missing chain id"),
                from = from,
                to = transaction.to?.let { parseAddress(it) },
                data = (transaction.data ?: transaction.input)?.parseHex() ?: ByteString(),
                value = transaction.value?.parseHexNumber() ?: BigInteger.ZERO,
                gas = transaction.gas?.parseHexNumber()?.ulongValue(),
            )
        }

        else -> raise(WcInvalidRequest.MethodNotSupported(method))
    }
}

private fun String.parseHexNumber() = removePrefix("0x").toBigInteger(16)

private fun String.parseNumber(): BigInteger = if (startsWith("0x")) {
    removePrefix("0x").toBigInteger(16)
} else {
    toBigInteger()
}

private fun String.parseHex() = ByteString(removePrefix("0x").hexToByteString())

private fun Raise<WcFailure>.parseAddress(address: String) =
    withError({ WcInvalidRequest.Other(it.message) }) { Address(address).bind() }
