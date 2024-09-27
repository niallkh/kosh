package kosh.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kosh.ui.component.scaffold.LocalSnackbarHostState
import kosh.ui.navigation.hosts.RootHost
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.stack.StackRouter

@Composable
fun App(
    stackRouter: StackRouter<RootRoute>,
) {
    KoshTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val snackbarHostState = remember { SnackbarHostState() }
            CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            modifier = Modifier.imePadding(),
                            hostState = snackbarHostState
                        )
                    },
                ) { _ ->
                    RootHost(stackRouter = stackRouter)
                }
            }
        }
    }
}
