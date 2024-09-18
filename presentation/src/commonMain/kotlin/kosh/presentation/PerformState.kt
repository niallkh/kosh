package kosh.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import arrow.core.raise.Raise
import arrow.core.raise.recover
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.serializer

@Stable
class PerformState<P : Any, R : Any, E>(
    params: P?,
    result: R?,
) {
    var inProgress: Boolean by mutableStateOf(false)
    var failure: E? by mutableStateOf(null)
    var retry: Int by mutableIntStateOf(0)
    var params: P? by mutableStateOf(params)
    var result: R? by mutableStateOf(result)
    val performed: Boolean by derivedStateOf { this.result != null }

    operator fun invoke(params: P) {
        this.params = params
        result = null
    }

    fun retry() {
        retry++
    }

    @Composable
    fun Perform(
        vararg keys: Any?,
        block: suspend Raise<E>.(P) -> R,
    ) {
        LaunchedEffect(*keys, params, retry) {
            val p = params ?: return@LaunchedEffect
            result = null
            inProgress = true

            recover({
                result = block(p)
                params = null
                inProgress = false
                failure = null
            }) {
                failure = it
                inProgress = false
            }
        }
    }

    companion object {
        fun <P : Any, R : Any, E> Saver(
            paramsSerializer: KSerializer<P>,
            resultSerializer: KSerializer<R>,
        ): Saver<PerformState<P, R, E>, List<ByteArray>> = Saver(
            save = { state ->
                listOf(
                    Cbor.encodeToByteArray(paramsSerializer.nullable, state.params),
                    Cbor.encodeToByteArray(resultSerializer.nullable, state.result),
                )
            },
            restore = { list ->
                PerformState(
                    params = Cbor.decodeFromByteArray(paramsSerializer.nullable, list[0]),
                    result = Cbor.decodeFromByteArray(resultSerializer.nullable, list[1])
                )
            },
        )
    }
}

operator fun PerformState<Unit, *, *>.invoke() = invoke(Unit)

@Composable
inline fun <reified P : Any, reified R : Any, reified E> rememberPerform(): PerformState<P, R, E> =
    rememberSaveable(saver = PerformState.Saver(serializer(), serializer())) {
        PerformState(null, null)
    }

@Composable
inline fun <reified P : Any, reified R : Any, reified E> Perform(
    vararg keys: Any?,
    crossinline block: suspend Raise<E>.(P) -> R,
): PerformState<P, R, E> {
    val actionState = rememberPerform<P, R, E>()

    actionState.Perform(*keys) {
        block(it)
    }

    return actionState
}

@Composable
inline fun <reified R : Any, reified E> Perform(
    vararg keys: Any?,
    crossinline block: suspend Raise<E>.() -> R,
): PerformState<Unit, R, E> {
    val actionState = rememberPerform<Unit, R, E>()

    actionState.Perform(*keys) {
        block(this)
    }

    return actionState
}

@Composable
inline fun Perform(
    vararg keys: Any?,
    crossinline block: suspend () -> Unit,
): PerformState<Unit, Unit, Any> {
    val actionState = rememberPerform<Unit, Unit, Any>()

    actionState.Perform(*keys) {
        block()
    }

    return actionState
}



