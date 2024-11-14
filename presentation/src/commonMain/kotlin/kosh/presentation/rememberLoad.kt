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
import arrow.core.raise.Raise
import arrow.core.raise.recover
import kosh.presentation.di.rememberRetained
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@Composable
fun <F, R> rememberLoad(
    vararg keys: Any?,
    load: @DisallowComposableCalls suspend Raise<F>.() -> R,
): LoadState<F, R> {
    var inProgress by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<F?>(null) }
    var result by rememberRetained { mutableStateOf<R?>(null) }

    val currentLoad by rememberUpdatedState(load)

    val loadLauncher = LoadLauncher(*keys) {
        inProgress = true

        recover({
            val res = currentLoad()

            inProgress = false
            result = res
        }) {
            inProgress = false
            failure = it
        }
    }

    val currentLoadLauncher by rememberUpdatedState(loadLauncher)

    return remember {
        object : LoadState<F, R> {
            override val loading: Boolean get() = inProgress
            override val failure: F? get() = failure
            override val result: R? get() = result
            override fun retry() = currentLoadLauncher.retry()
        }
    }
}

@Composable
@NonRestartableComposable
private fun LoadLauncher(
    vararg keys: Any?,
    block: suspend () -> Unit,
): LoadLauncher {
    val applyContext = rememberCoroutineScope().coroutineContext
    return remember(*keys) { LoadLauncher(applyContext, block) }
}

private class LoadLauncher(
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
interface LoadState<F, R> {
    val loading: Boolean
    val failure: F?
    val result: R?

    fun retry()
}
