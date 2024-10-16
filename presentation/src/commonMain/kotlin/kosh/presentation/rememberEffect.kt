package kosh.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kosh.presentation.di.rememberRetained

@Composable
inline fun <reified R> rememberEffect(
    vararg keys: Any?,
    noinline effect: @DisallowComposableCalls suspend () -> R,
): EffectState<R> {
    val effectState = rememberRetained { EffectState<R>() }

    effectState(
        keys = keys,
        effect = effect
    )

    return effectState
}

@Stable
class EffectState<R> {
    var inProgress by mutableStateOf(false)
    var finished by mutableStateOf(false)
    var retry by mutableIntStateOf(0)

    var result: R? by mutableStateOf(null)

    operator fun invoke() {
        retry++
        result = null
        finished = false
    }

    @Composable
    operator fun invoke(
        vararg keys: Any?,
        effect: suspend () -> R,
    ) {
        LaunchedEffect(*keys, retry) {
            inProgress = true
            try {
                result = effect()
                finished = true
                inProgress = false
            } finally {
                inProgress = false
            }
        }
    }
}
