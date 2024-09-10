package kosh.domain.models.web3

import androidx.compose.runtime.Immutable
import arrow.optics.optics
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.serializers.BigInteger
import kotlinx.serialization.Serializable

@Immutable
@Serializable
@optics
data class Transaction(
    val chainId: ChainId,
    val from: Address,
    val to: Address?,
    val input: ByteString,
    val value: BigInteger,
    val gas: ULong?,
) {
    companion object
}
