package kosh.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.core.raise.Raise
import arrow.core.raise.recover
import kosh.domain.failure.AppFailure
import kosh.presentation.di.rememberRetained
import kotlinx.coroutines.flow.Flow

@Stable
@Deprecated("")
class ContentState<E : AppFailure, C : Any> {
    var init: Boolean by mutableStateOf(false)
    var loading: Boolean by mutableStateOf(false)
    var failure: E? by mutableStateOf(null)
    var content: C? by mutableStateOf(null)
    private var retry: Int by mutableIntStateOf(0)

    @Deprecated("")
    fun retry() {
        retry++
    }

    @Composable
    @Deprecated("")
    fun Load(
        vararg keys: Any?,
        block: suspend Raise<E>.() -> C,
    ) {
        LaunchedEffect(*keys, retry) {
            loading = true

            recover({
                content = block()

                init = true
                loading = false
                failure = null
            }) {
                init = true
                failure = it
                loading = false
            }
        }
    }

    @Composable
    @Deprecated("")
    fun Collect(
        vararg keys: Any?,
        block: suspend Raise<E>.() -> Flow<C>,
    ) {
        LaunchedEffect(*keys, retry) {
            loading = true

            recover({
                block().collect {
                    init = true
                    loading = false
                    failure = null

                    content = it
                }
            }) {
                init = true
                failure = it
                loading = false
            }
        }
    }
}

@Composable
@Deprecated("")
inline fun <reified E : AppFailure, reified C : Any> rememberContent(): ContentState<E, C> =
    rememberRetained { ContentState() }

@Composable
@Deprecated("")
inline fun <reified E : AppFailure, reified C : Any> Load(
    vararg keys: Any?,
    noinline block: suspend Raise<E>.() -> C,
): ContentState<E, C> {
    val contentState = rememberContent<E, C>()

    contentState.Load(*keys) {
        block()
    }

    return contentState
}
