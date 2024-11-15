package kosh.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import kosh.presentation.di.rememberRetained
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@Composable
fun <R> rememberCollect(
    initial: R,
    vararg inputs: Any?,
    flow: @DisallowComposableCalls suspend () -> Flow<R>,
): CollectState<R> {
    var init by rememberRetained { mutableStateOf(false) }
    var result by rememberRetained { mutableStateOf(initial) }

    val currentFlow by rememberUpdatedState(flow)

    val collectLauncher = CollectLauncher(*inputs) {
        currentFlow().collect {
            init = true
            result = it
        }
        init = true
    }

    val currentCollectLauncher by rememberUpdatedState(collectLauncher)

    return remember {
        object : CollectState<R> {
            override val init: Boolean get() = init
            override val result: R get() = result
            override fun retry() = currentCollectLauncher.retry()
        }
    }
}

@Composable
@NonRestartableComposable
private fun CollectLauncher(
    vararg inputs: Any?,
    block: suspend () -> Unit,
): CollectLauncher {
    val applyContext = rememberCoroutineScope().coroutineContext
    return remember(*inputs) { CollectLauncher(applyContext, block) }
}

private class CollectLauncher(
    context: CoroutineContext,
    private val block: suspend () -> Unit,
) : RememberObserver {
    private val scope: CoroutineScope = CoroutineScope(context + SupervisorJob())
    private var job: Job? = null
    private var active = true

    init {
        retry()
    }

    fun retry() {
        if (!active) return
        job?.cancel()
        job = scope.launch {
            block()
        }
    }

    override fun onRemembered() {
        active = true
    }

    override fun onForgotten() {
        active = false
        job?.cancel()
    }

    override fun onAbandoned() {
        active = false
        scope.cancel()
    }
}


@Stable
interface CollectState<R> {
    val init: Boolean
    val result: R

    fun retry()
}
