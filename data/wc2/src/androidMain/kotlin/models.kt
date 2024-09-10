package kosh.data.wc2

import kotlinx.serialization.Serializable

@Serializable
data class AddEthereumNetwork(
    val chainId: String,
    val chainName: String?,
    val nativeCurrency: Token?,
    val rpcUrls: List<String>? = null,
    val blockExplorerUrls: List<String>? = null,
    val iconUrls: List<String>? = null,
) {

    @Serializable
    data class Token(
        val name: String?,
        val symbol: String?,
        val decimals: Int?,
    )
}

@Serializable
data class WatchAsset(
    val type: String,
    val options: Options,
) {

    @Serializable
    data class Options(
        val address: String,
        val tokenId: String? = null,
        val image: String? = null,
    )
}

@Serializable
data class SignTransaction(
    val chainId: String? = null,
    val to: String? = null,
    val from: String,
    val gas: String? = null,
    val value: String? = null,
    val data: String? = null,
    val input: String? = null,
    val gasPrice: String? = null,
    val maxPriorityFeePerGas: String? = null,
    val maxFeePerGas: String? = null,
    val nonce: String? = null,
    val type: String? = null,
)
