package kosh.domain.models.token

import androidx.compose.runtime.Immutable
import kosh.domain.serializers.BigInteger
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class Balance(
    val value: BigInteger,
    val failed: Boolean = false,
) {

    companion object {
        private val Empty = Balance(BigInteger.ZERO)

        operator fun invoke(): Balance = Empty
    }
}

operator fun Balance.plus(other: Balance) = Balance(value + other.value)
