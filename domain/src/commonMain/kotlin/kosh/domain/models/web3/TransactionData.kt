package kosh.domain.models.web3

import androidx.compose.runtime.Immutable
import arrow.optics.optics
import kotlinx.serialization.Serializable

@Serializable
@Immutable
@optics
data class TransactionData(
    val tx: Transaction,
    val nonce: ULong,
    val gasLimit: ULong,
    val gasPrice: GasPrice,
) {

    companion object
}
