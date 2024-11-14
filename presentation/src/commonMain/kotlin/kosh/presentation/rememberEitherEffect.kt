package kosh.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import arrow.core.raise.Raise
import arrow.core.raise.either

@Composable
fun <F, R, P> rememberEitherEffect(
    vararg keys: Any?,
    onFinish: (R) -> Unit = {},
    effect: @DisallowComposableCalls suspend Raise<F>.(P) -> R,
): EitherEffectState<F, R, P> {
    val either = rememberEffect(*keys, onFinish = { it.onRight(onFinish) }) { param: P ->
        either { effect(param) }
    }

    return remember {
        object : EitherEffectState<F, R, P> {
            override val inProgress: Boolean get() = either.inProgress
            override val done: Boolean get() = either.done
            override val result: R? get() = either.result?.getOrNull()
            override val failure: F? get() = either.result?.leftOrNull()
            override fun invoke(param: P) = either(param)
        }
    }
}

@Stable
interface EitherEffectState<F, R, P> {
    val inProgress: Boolean
    val done: Boolean
    val result: R?
    val failure: F?

    operator fun invoke(param: P)
}
