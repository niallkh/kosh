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
inline fun <reified R> rememberLoad(
    vararg keys: Any?,
    noinline flow: @DisallowComposableCalls suspend () -> R,
): LoadState<R> {
    val collectState = rememberRetained { LoadState<R>() }

    collectState(
        keys = keys,
        load = flow,
    )

    return collectState
}

@Stable
class LoadState<R> {
    var loading by mutableStateOf(false)
    var retry by mutableIntStateOf(0)

    var result: R? by mutableStateOf(null)

    operator fun invoke() {
        retry++
        result = null
    }

    @Composable
    operator fun invoke(
        vararg keys: Any?,
        load: suspend () -> R,
    ) {
        LaunchedEffect(*keys, retry) {
            loading = true
            try {
                result = load()
                loading = false
            } finally {
                loading = false
            }
        }
    }
}
