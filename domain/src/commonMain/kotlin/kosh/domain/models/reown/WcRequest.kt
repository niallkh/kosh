package kosh.domain.models.reown

import androidx.compose.runtime.Immutable
import arrow.optics.optics
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.JsonTypedData
import kosh.domain.models.web3.Transaction
import kosh.domain.serializers.BigInteger
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@Immutable
data class WcRequest(
    val id: Id,
    val sessionTopic: SessionTopic,
    val dapp: DappMetadata,
    val call: Call,
    val verifyContext: WcVerifyContext,
) {

    @Serializable
    @Immutable
    @optics
    sealed class Call {

        @Serializable
        @Immutable
        @optics
        @SerialName("personalSign")
        data class SignPersonal(
            val message: EthMessage,
            val account: Address,
        ) : Call() {
            companion object
        }

        @Serializable
        @Immutable
        @optics
        @SerialName("signTypedData")
        data class SignTyped(
            val chainId: ChainId?,
            val account: Address,
            val json: JsonTypedData,
        ) : Call() {
            companion object
        }

        @Serializable
        @Immutable
        @optics
        @SerialName("addEthereumChain")
        data class AddNetwork(
            val chainId: ChainId,
            val chainName: String,
            val tokenName: String,
            val tokenSymbol: String,
            val tokenDecimals: UByte,
            val rpcProviders: List<Uri>,
            val explorers: List<Uri>,
            val icons: List<Uri>,
        ) : Call() {
            companion object
        }

        @Serializable
        @Immutable
        @optics
        @SerialName("watchAsset")
        data class WatchAsset(
            val chainId: ChainId,
            val address: Address,
            val tokenId: BigInteger?,
            val icon: Uri?,
        ) : Call() {
            companion object
        }

        @Serializable
        @Immutable
        @optics
        @SerialName("sendTransaction")
        data class SendTransaction(
            val chainId: ChainId,
            val to: Address?,
            val from: Address,
            val gas: ULong?,
            val value: BigInteger,
            val data: ByteString,
            val maxFeePerGas: BigInteger?,
            val maxPriorityFeePerGas: BigInteger?,
            val nonce: BigInteger?,
        ) : Call() {
            companion object
        }

        companion object
    }

    @JvmInline
    @Serializable
    @Immutable
    value class Id(val value: Long)

    companion object
}


fun WcRequest.Call.SendTransaction.toTransactionData() = Transaction(
    chainId = chainId,
    from = from,
    to = to,
    input = data,
    value = value,
    gas = gas,
)
