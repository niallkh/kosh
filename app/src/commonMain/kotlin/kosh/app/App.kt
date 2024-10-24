package kosh.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.ui.navigation.hosts.RootHost
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.stack.StackRouter

@Composable
internal fun App(
    stackRouter: StackRouter<RootRoute>,
) {
    KoshTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            RootHost(stackRouter = stackRouter)
        }
    }
}
