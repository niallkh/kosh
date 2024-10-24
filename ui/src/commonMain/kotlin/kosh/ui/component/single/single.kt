package kosh.ui.component.single

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeSource

private val clickTimeout = 300.milliseconds

private typealias OnClick = () -> Unit

fun interface SingleClick : OnClick

private fun SingleClick(provider: State<OnClick>): SingleClick {
    var lastEvent = TimeSource.Monotonic.markNow()
    return SingleClick {
        if (lastEvent.elapsedNow() >= clickTimeout) {
            provider.value.invoke()
            lastEvent = TimeSource.Monotonic.markNow()
        }
    }
}

@Composable
fun OnClick.single(): OnClick {
    if (this is SingleClick) {
        return this
    }
    val callback = rememberUpdatedState(this)
    return remember { SingleClick(callback) }
}
