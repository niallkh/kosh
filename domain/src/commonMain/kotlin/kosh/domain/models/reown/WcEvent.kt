package kosh.domain.models.reown

import androidx.compose.runtime.Immutable
import kosh.domain.models.ChainId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@Immutable
data class WcEvent(
    val sessionTopic: SessionTopic,
    val chainId: ChainId,
    val data: Data,
) {

    @Serializable
    @Immutable
    sealed class Data {

        @Serializable
        @Immutable
        @SerialName("message")
        data class Message(
            val type: String,
            val data: String,
        ) : Data()
    }

    @JvmInline
    @Serializable
    @Immutable
    value class Id(val value: Long)
}
