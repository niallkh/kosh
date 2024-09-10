package kosh.domain.utils

import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.yield

suspend inline fun cancellation(): Nothing {
    currentCoroutineContext().cancel()
    yield()
    error("This code should never reach")
}
