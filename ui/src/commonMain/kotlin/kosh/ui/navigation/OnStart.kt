package kosh.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kotlinx.coroutines.Job

@Composable
fun OnStart(
    whileStarted: () -> Job,
) {
    DisposableEffect(Unit) {
        val job = whileStarted()

        onDispose {
            job.cancel()
        }
    }
}
