package kosh.libs.reown

import kotlinx.serialization.Serializable

data class StringWrapper(val value: String)

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
data class SendTransaction(
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

@Serializable
data class SessionProposal(
    val id: Long,
    val pairingTopic: String,
    val name: String?,
    val url: String?,
    val icon: String?,
    val description: String?,
    val verifyContext: VerifyContext,
    val required: Namespace,
    val optional: Namespace,
) {

    @Serializable
    data class Namespace(
        val chains: List<String>,
        val methods: List<String>,
        val events: List<String>,
    )
}

@Serializable
data class AuthenticationRequest(
    val id: Long,
    val pairingTopic: String,
    val name: String?,
    val url: String?,
    val icon: String?,
    val description: String?,
    val verifyContext: VerifyContext,
)

@Serializable
data class SessionRequest(
    val id: Long,
    val sessionTopic: String,
    val name: String?,
    val url: String?,
    val icon: String?,
    val description: String?,
    val chainId: ULong?,
    val method: String,
    val params: String,
    val verifyContext: VerifyContext,
)

@Serializable
data class Session(
    val sessionTopic: String,
    val name: String?,
    val url: String?,
    val icon: String?,
    val description: String?,
    val required: SessionProposal.Namespace,
    val optional: SessionProposal.Namespace,
    val approved: Namespace,
) {

    @Serializable
    data class Namespace(
        val chains: List<String>,
        val accounts: List<String>,
        val methods: List<String>,
        val events: List<String>,
    )
}

enum class VerifyContext {
    Match,
    Unverified,
    Mismatch,
    Threat,
}
