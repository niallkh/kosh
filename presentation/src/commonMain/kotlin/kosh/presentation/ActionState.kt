package kosh.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import arrow.core.raise.Raise
import arrow.core.raise.recover
import kosh.domain.failure.AppFailure
import kotlinx.serialization.KSerializer
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.serializer

@Stable
class ActionState<E : AppFailure, P : Any>(
    initial: P?,
) {
    var inProgress: Boolean by mutableStateOf(false)
    var failure: E? by mutableStateOf(null)
    var retry: Int by mutableIntStateOf(0)
    var performed: Boolean by mutableStateOf(false)
    var params: P? by mutableStateOf(initial)

    operator fun invoke(params: P) {
        this.params = params
        performed = false
    }

    fun retry() {
        retry++
    }

    @Composable
    fun Perform(
        vararg keys: Any?,
        block: suspend Raise<E>.(P) -> Unit,
    ) {
        LaunchedEffect(*keys, params, retry) {
            val p = params ?: return@LaunchedEffect
            inProgress = true

            recover({
                block(p)
                params = null
                performed = true
                inProgress = false
                failure = null
            }) {
                failure = it
                inProgress = false
            }
        }
    }

    companion object {
        fun <E : AppFailure, P : Any> Saver(
            serializer: KSerializer<P>,
        ): Saver<ActionState<E, P>, ByteArray> = Saver(
            save = { state ->
                state.params
                    ?.let { Cbor.encodeToByteArray(serializer, it) }
                    ?: ByteArray(0)
            },
            restore = { bytes ->
                bytes.takeIf { it.isNotEmpty() }
                    ?.let { Cbor.decodeFromByteArray(serializer, it) }
                    .let { ActionState(it) }
            },
        )
    }
}

@Composable
inline fun <reified E : AppFailure, reified P : Any> rememberAction(): ActionState<E, P> =
    rememberSaveable(saver = ActionState.Saver(serializer())) {
        ActionState(null)
    }

@Composable
inline fun <reified P : Any, reified E : AppFailure> PerformAction(
    vararg keys: Any?,
    crossinline block: suspend Raise<E>.(P) -> Unit,
): ActionState<E, P> {
    val actionState = rememberAction<E, P>()

    actionState.Perform(*keys) {
        block(it)
    }

    return actionState
}



