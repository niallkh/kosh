package kosh.presentation.ticker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.selects.onTimeout
import kotlinx.coroutines.selects.select
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Stable
class Ticker(
    private val period: Duration,
    initial: Instant = Instant.DISTANT_PAST,
) {
    var last: Instant by mutableStateOf(initial)

    private val resetChannel = Channel<Unit>()

    suspend fun waitNext() {
        val now = Clock.System.now()
        val next = last + period
        if (next > now) {
            select {
                onTimeout(next - now) {
                    last = next
                }
                resetChannel.onReceive {
                    last = Clock.System.now()
                }
            }
        } else {
            last = now
        }
    }

    fun reset() {
        last = Instant.DISTANT_PAST
        resetChannel.trySend(Unit)
    }

    companion object {
        val Saver = Saver<Ticker, Array<Long>>(
            save = {
                arrayOf(
                    it.last.epochSeconds,
                    it.period.inWholeSeconds,
                )
            },
            restore = {
                val (initial, period) = it
                Ticker(
                    period.seconds,
                    Instant.fromEpochSeconds(initial),
                )
            }
        )
    }
}

@Composable
fun rememberTicker(period: Duration): Ticker = rememberSaveable(period, saver = Ticker.Saver) {
    Ticker(period)
}


