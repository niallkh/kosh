@file:OptIn(ExperimentalTypeInference::class)

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
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.experimental.ExperimentalTypeInference

@Composable
fun <R, P> rememberEffect(
    vararg inputs: Any?,
    onFinish: (R) -> Unit = {},
    effect: @DisallowComposableCalls suspend (P) -> R,
): EffectState<R, P> {
    var inProgress by remember { mutableStateOf(false) }
    var done by rememberRetained { mutableStateOf(false) }
    var result by rememberRetained { mutableStateOf<R?>(null) }

    val currentEffect by rememberUpdatedState(effect)
    val currentOnFinish by rememberUpdatedState(onFinish)

    val effectLauncher = EffectLauncher<P>(*inputs) { param ->
        inProgress = true

        val res = currentEffect(param)

        result = res
        inProgress = false
        done = true
        currentOnFinish(res)
    }

    val currentEffectLauncher by rememberUpdatedState(effectLauncher)

    return remember {
        object : EffectState<R, P> {
            override val inProgress: Boolean get() = inProgress
            override val done: Boolean get() = done
            override val result: R? get() = result
            override fun invoke(param: P) = currentEffectLauncher.run(param)
        }
    }
}

@Composable
@NonRestartableComposable
private fun <P> EffectLauncher(
    vararg inputs: Any?,
    block: suspend (P) -> Unit,
): EffectLauncher<P> {
    val applyContext = rememberCoroutineScope().coroutineContext
    return remember(*inputs) { EffectLauncher(applyContext, block) }
}

private class EffectLauncher<P>(
    context: CoroutineContext,
    private val block: suspend (P) -> Unit,
) : RememberObserver {
    private val scope = CoroutineScope(context + SupervisorJob())
    private var job: Job? = null
    private var active = true

    fun run(param: P) {
        if (!active) return
        job?.cancel("EffectLauncher new run")
        job = scope.launch {
            block(param)
        }
    }

    override fun onRemembered() {
        active = true
    }

    override fun onForgotten() {
        active = false
        job?.cancel("EffectLauncher forgotten")
    }

    override fun onAbandoned() {
        active = false
        scope.cancel("EffectLauncher abandoned")
    }
}


@Stable
interface EffectState<R, P> {
    val inProgress: Boolean
    val done: Boolean
    val result: R?

    operator fun invoke(param: P)
}
