package kosh.ui.navigation.hosts.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.channels.Channel

fun interface HomeTabResetHandler : () -> Channel<Unit>

val LocalHomeTabResetHandler = compositionLocalOf { HomeTabResetHandler() }

fun HomeTabResetHandler(): HomeTabResetHandler {
    val channel = Channel<Unit>()
    return HomeTabResetHandler { channel }
}

@Composable
fun HandleHomeTabReset(callback: () -> Unit) {
    val homeTabResetHandler = LocalHomeTabResetHandler.current
    LaunchedEffect(callback) {
        for (i in homeTabResetHandler()) {
            callback()
        }
    }
}
