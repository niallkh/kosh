package kosh.domain.models.reown

import androidx.compose.runtime.Immutable
import kosh.domain.models.ChainId
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@Immutable
data class WcSessionProposal(
    val id: Id,
    val dapp: DappMetadata,
    val requestId: Long,
    val verifyContext: WcVerifyContext,
    val required: Namespace,
    val optional: Namespace,
) {

    @JvmInline
    @Serializable
    @Immutable
    value class Id(val pairingTopic: PairingTopic)

    @Immutable
    @Serializable
    data class Namespace(
        val chains: List<ChainId>,
        val methods: List<String>,
        val events: List<String>,
    )
}
