package kosh.domain.models.web3

import androidx.compose.runtime.Immutable
import arrow.optics.optics
import kosh.domain.models.Hash
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
@Immutable
@optics
data class Receipt(
    val transactionHash: Hash,
    val blockHash: Hash,
    val gasUsed: ULong,
    val gasPrice: GasPrice,
    val success: Boolean,
    val time: Instant,
) {
    companion object
}
