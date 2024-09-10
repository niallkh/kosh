package kosh.domain.models.web3

import androidx.compose.runtime.Immutable
import arrow.optics.optics
import kosh.domain.serializers.BigInteger
import kotlinx.serialization.Serializable

@Serializable
@Immutable
@optics
data class GasPrice(
    val base: BigInteger,
    val priority: BigInteger,
) {
    companion object {
        private val EMPTY = GasPrice(BigInteger.ZERO, BigInteger.ZERO)

        operator fun invoke() = EMPTY
    }
}

@Serializable
@Immutable
@optics
data class GasPrices(
    val current: GasPrice,
    val slow: GasPrice,
    val medium: GasPrice,
    val fast: GasPrice,
) {
    companion object {
        private val EMPTY = GasPrices(GasPrice(), GasPrice(), GasPrice(), GasPrice())

        operator fun invoke() = EMPTY
    }
}

val GasPrice.gasPrice: BigInteger
    inline get() = base + priority
