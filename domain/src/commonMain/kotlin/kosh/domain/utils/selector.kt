package kosh.domain.utils

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

suspend fun <T> StateFlow<T>.select(
    block: suspend (T) -> Unit,
): Nothing {
    var last = value
    collect {
        if (it != last) {
            last = it
            block(it)
        } else {
            last = it
        }
    }
}

suspend fun <T> StateFlow<T>.selectLatest(
    block: suspend (T) -> Unit,
) {
    var last = value
    collectLatest {
        if (it != last) {
            last = it
            block(it)
        } else {
            last = it
        }
    }
}
