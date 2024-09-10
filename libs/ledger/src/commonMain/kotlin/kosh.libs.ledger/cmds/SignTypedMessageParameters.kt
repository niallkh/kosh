package kosh.libs.ledger.cmds

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class SignTypedMessageParameters(
    val filters: Eip712Filters? = null,
    val tokens: List<TokenInfo> = emptyList(),
)

@Serializable
data class Eip712Filters(
    val contractName: Info,
    val fields: List<Filter>,
) {

    @Serializable
    data class Info(
        val label: String,
        val signature: String,
    )

    @Serializable
    data class Filter(
        val label: String,
        val path: String,
        val signature: String,
        val format: Format? = null,
        @SerialName("coin_ref")
        val coinRef: Int? = null,
    )

    enum class Format {
        @SerialName("datetime")
        DATETIME,

        @SerialName("token")
        TOKEN,

        @SerialName("amount")
        AMOUNT,

        @SerialName("raw")
        RAW,
    }
}
