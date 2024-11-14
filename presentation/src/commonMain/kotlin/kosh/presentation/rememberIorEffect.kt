package kosh.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import arrow.core.Ior

@Composable
fun <F, R, P> rememberIorEffect(
    vararg keys: Any?,
    onFinish: (R) -> Unit = {},
    effect: @DisallowComposableCalls suspend (P) -> Ior<F, R>,
): IorEffectState<F, R, P> {
    val either = rememberEffect(*keys, onFinish = { it.map(onFinish) }) { param: P ->
        effect(param)
    }

    return remember {
        object : IorEffectState<F, R, P> {
            override val inProgress: Boolean get() = either.inProgress
            override val done: Boolean get() = either.done
            override val both: Boolean get() = either.result?.isBoth() ?: false
            override val result: R? get() = either.result?.getOrNull()
            override val failure: F? get() = either.result?.leftOrNull()
            override fun invoke(param: P) = either(param)
        }
    }
}

@Stable
interface IorEffectState<F, R, P> {
    val inProgress: Boolean
    val done: Boolean
    val both: Boolean
    val result: R?
    val failure: F?

    operator fun invoke(param: P)
}
