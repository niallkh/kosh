package kosh.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kosh.presentation.di.rememberLifecycleState
import kosh.presentation.di.rememberRetained
import kotlinx.coroutines.flow.Flow

@Composable
inline fun <reified R> rememberCollect(
    initial: R,
    vararg keys: Any?,
    noinline flow: @DisallowComposableCalls suspend () -> Flow<R>,
): CollectState<R> {
    val collectState = rememberRetained { CollectState(initial) }

    if (rememberLifecycleState()) {
        collectState(
            keys = keys,
            flow = flow
        )
    }

    return collectState
}

@Stable
class CollectState<R>(
    private val initial: R,
) {
    var loading by mutableStateOf(false)
    var retry by mutableIntStateOf(0)

    var result: R by mutableStateOf(initial)

    operator fun invoke() {
        retry++
        result = initial
    }

    @Composable
    operator fun invoke(
        vararg keys: Any?,
        flow: suspend () -> Flow<R>,
    ) {
        LaunchedEffect(*keys, retry) {
            loading = true
            try {
                flow().collect {
                    result = it
                    loading = false
                }
            } finally {
                loading = false
            }
        }
    }
}
