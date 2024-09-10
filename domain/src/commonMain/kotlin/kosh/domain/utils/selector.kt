package kosh.domain.utils

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

suspend fun <T> StateFlow<T>.selector(
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

suspend fun <T> StateFlow<T>.selectorLatest(
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
