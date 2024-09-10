package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kosh.domain.models.web3.GasPrices
import kosh.presentation.di.rememberSerializable

@Composable
fun rememberGasSpeed(): GasSpeedState {
    var gasSpeed by rememberSerializable { mutableStateOf(GasSpeed.Medium) }

    return GasSpeedState(
        speed = gasSpeed,
        change = { gasSpeed = it }
    )
}

data class GasSpeedState(
    val speed: GasSpeed,
    val change: (GasSpeed) -> Unit,
)

enum class GasSpeed {
    Slow,
    Medium,
    Fast,
}

fun GasPrices.get(selected: GasSpeed) = when (selected) {
    GasSpeed.Slow -> slow
    GasSpeed.Medium -> medium
    GasSpeed.Fast -> fast
}
