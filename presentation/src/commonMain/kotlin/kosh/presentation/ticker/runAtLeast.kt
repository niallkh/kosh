package kosh.presentation.ticker

import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeSource

suspend inline fun <T> runAtLeast(
    time: Duration = 300.milliseconds,
    block: () -> T,
): T {
    val started = TimeSource.Monotonic.markNow()

    val result = block()

    (time - started.elapsedNow())
        .takeIf { it.isPositive() }
        ?.let { delay(it) }
    return result
}
