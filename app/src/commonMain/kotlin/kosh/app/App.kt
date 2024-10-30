package kosh.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.ui.Modifier
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.hosts.home.HomeHost
import kosh.ui.navigation.routes.RootRoute

@Composable
internal fun App(
    initialLink: RootRoute?,
    onResult: @DisallowComposableCalls (RouteResult.Result) -> Unit,
) {
    KoshTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            HomeHost(
                initialLink = initialLink,
                onResult = onResult,
            )
        }
    }
}
