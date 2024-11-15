package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kosh.domain.models.web3.GasPrices
import kosh.presentation.di.rememberSerializable

@Composable
fun rememberGasSpeed(): GasSpeedState {
    var gasSpeed by rememberSerializable { mutableStateOf(GasSpeed.Medium) }

    return remember {
        object : GasSpeedState {
            override val speed: GasSpeed get() = gasSpeed
            override fun change(speed: GasSpeed) {
                gasSpeed = speed
            }
        }
    }
}

@Stable
interface GasSpeedState {
    val speed: GasSpeed
    fun change(speed: GasSpeed)
}

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
