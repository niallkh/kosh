package kosh.domain.models.wc

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline


@Serializable
@Immutable
data class WcSession(
    val id: Id,
    val dapp: DappMetadata,
) {

    @JvmInline
    @Serializable
    @Immutable
    value class Id(val sessionTopic: SessionTopic)
}
