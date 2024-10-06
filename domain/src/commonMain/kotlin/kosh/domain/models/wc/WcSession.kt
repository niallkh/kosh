package kosh.domain.models.wc

import androidx.compose.runtime.Immutable
import kosh.domain.models.ChainAddress
import kosh.domain.models.ChainId
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline


@Serializable
@Immutable
data class WcSession(
    val id: Id,
    val dapp: DappMetadata,
    val required: WcSessionProposal.Namespace,
    val optional: WcSessionProposal.Namespace,
    val approved: Namespace,
) {

    @JvmInline
    @Serializable
    @Immutable
    value class Id(val sessionTopic: SessionTopic)

    @Immutable
    @Serializable
    data class Namespace(
        val chains: List<ChainId>,
        val accounts: List<ChainAddress>,
        val methods: List<String>,
        val events: List<String>,
    )
}
