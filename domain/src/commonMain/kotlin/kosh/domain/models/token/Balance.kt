package kosh.domain.models.token

import androidx.compose.runtime.Immutable
import kosh.domain.serializers.BigInteger
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class Balance(
    val value: BigInteger,
    val updatedAt: Instant,
) {

    companion object {
        private val Empty = Balance(BigInteger.ZERO, Instant.DISTANT_PAST)

        operator fun invoke(): Balance {
            return Empty
        }
    }
}
