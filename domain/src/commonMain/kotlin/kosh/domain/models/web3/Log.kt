package kosh.domain.models.web3

import androidx.compose.runtime.Immutable
import arrow.optics.optics
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kotlinx.serialization.Serializable

@Serializable
@Immutable
@optics
data class Log(
    val address: Address,
    val topics: List<ByteString>,
    val data: ByteString,
) {
    companion object
}
