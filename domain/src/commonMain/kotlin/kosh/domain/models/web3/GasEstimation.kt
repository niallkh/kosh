package kosh.domain.models.web3

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class GasEstimation(
    val estimated: ULong,
    val gas: ULong,
) {

    companion object {
        private val EMPTY = GasEstimation(0u, 0u)

        operator fun invoke() = EMPTY
    }
}
