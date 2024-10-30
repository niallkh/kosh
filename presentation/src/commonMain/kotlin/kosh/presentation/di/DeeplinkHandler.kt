package kosh.presentation.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import co.touchlab.kermit.Logger
import kosh.presentation.core.di
import kotlinx.coroutines.channels.Channel

class DeeplinkHandler {

    private val logger = Logger.withTag("[K]")

    internal val channel = Channel<String?>(Channel.CONFLATED)

    fun handle(url: String?) {
        logger.v { "handle(url: $url)" }
        channel.trySend(url)
    }
}

@Composable
fun HandleDeeplink(
    handler: DeeplinkHandler = di { deeplinkHandler },
    callback: (String?) -> Unit,
) {
    LaunchedEffect(callback) {
        for (uri in handler.channel) {
            callback(uri)
        }
    }
}
