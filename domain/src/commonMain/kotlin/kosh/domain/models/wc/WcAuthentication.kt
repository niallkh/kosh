package kosh.domain.models.wc

import androidx.compose.runtime.Immutable
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.web3.EthMessage
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@Immutable
data class WcAuthentication(
    val id: Id,
    val pairingTopic: PairingTopic,
    val dapp: DappMetadata,
    val verifyContext: WcVerifyContext,
) {

    @JvmInline
    @Serializable
    @Immutable
    value class Id(val value: Long)

    @Serializable
    @Immutable
    data class Message(
        val chainId: ChainId,
        val account: Address,
        val msg: EthMessage,
    )
}
