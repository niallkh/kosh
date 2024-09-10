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
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.serializer

@Stable
class ContentState<E : AppFailure, C : Any>(
    initial: C?,
) {
    var loading: Boolean by mutableStateOf(false)
    var failure: E? by mutableStateOf(null)
    var content: C? by mutableStateOf(initial)
    private var retry: Int by mutableIntStateOf(0)

    fun retry() {
        retry++
    }

    @Composable
    fun Load(
        vararg keys: Any?,
        block: suspend Raise<E>.() -> C,
    ) {
        LaunchedEffect(*keys, retry) {
            loading = true

            recover({
                content = block()

                loading = false
                failure = null
            }) {
                failure = it
                loading = false
            }
        }
    }

    @Composable
    fun Collect(
        vararg keys: Any?,
        block: suspend Raise<E>.() -> Flow<C>,
    ) {
        LaunchedEffect(*keys, retry) {
            loading = true

            recover({
                block().collect {
                    loading = false
                    failure = null

                    content = it
                }
            }) {
                failure = it
                loading = false
            }
        }
    }

    companion object {
        fun <E : AppFailure, C : Any> Saver(
            serializer: KSerializer<C>,
        ): Saver<ContentState<E, C>, ByteArray> = Saver(
            save = { state ->
                state.content
                    ?.let { Cbor.encodeToByteArray(serializer, it) }
                    ?: ByteArray(0)
            },
            restore = { bytes ->
                bytes.takeIf { it.isNotEmpty() }
                    ?.let { Cbor.decodeFromByteArray(serializer, it) }
                    .let { ContentState(it) }
            },
        )
    }
}

@Composable
inline fun <reified E : AppFailure, reified C : Any> rememberContent(): ContentState<E, C> =
    rememberSaveable(saver = ContentState.Saver(serializer())) {
        ContentState(null)
    }

@Composable
inline fun <reified C : Any, reified E : AppFailure> LoadContent(
    vararg keys: Any?,
    noinline block: suspend Raise<E>.() -> C,
): ContentState<E, C> {
    val contentState = rememberContent<E, C>()

    contentState.Load(*keys) {
        block()
    }

    return contentState
}
