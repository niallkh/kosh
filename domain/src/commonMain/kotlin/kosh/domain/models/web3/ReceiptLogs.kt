package kosh.domain.models.web3

import androidx.compose.runtime.Immutable
import arrow.optics.optics
import kotlinx.serialization.Serializable

@Serializable
@Immutable
@optics
data class ReceiptLogs(
    val receipt: Receipt,
    val logs: List<Log>,
) {
    companion object
}
