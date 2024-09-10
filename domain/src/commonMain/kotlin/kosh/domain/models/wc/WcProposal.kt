package kosh.domain.models.wc

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@Immutable
data class WcProposal(
    val id: Id,
    val dapp: DappMetadata,
    val requestId: Long,
    val verifyContext: WcVerifyContext,
) {

    @JvmInline
    @Serializable
    @Immutable
    value class Id(val pairingTopic: PairingTopic)
}
