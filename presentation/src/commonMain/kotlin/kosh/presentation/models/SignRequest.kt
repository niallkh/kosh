package kosh.presentation.models

import androidx.compose.runtime.Immutable
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.reown.DappMetadata
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.JsonTypeData
import kosh.domain.models.web3.Signature
import kosh.domain.models.web3.TransactionData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Immutable
sealed interface SignRequest {
    val account: Address

    @Serializable
    @SerialName("personalSign")
    @Immutable
    data class SignPersonal(
        val chainId: ChainId?,
        override val account: Address,
        val message: EthMessage,
        val dapp: DappMetadata,
    ) : SignRequest

    @Serializable
    @SerialName("signTypedData")
    @Immutable
    data class SignTyped(
        val chainId: ChainId?,
        override val account: Address,
        val json: JsonTypeData,
        val dapp: DappMetadata,
    ) : SignRequest

    @Serializable
    @SerialName("signTransaction")
    @Immutable
    data class SignTransaction(
        val data: TransactionData,
        val dapp: DappMetadata,
    ) : SignRequest {
        override val account: Address
            get() = data.tx.from
    }
}

@Serializable
@Immutable
data class SignedRequest(
    val request: SignRequest,
    val signature: Signature,
)
