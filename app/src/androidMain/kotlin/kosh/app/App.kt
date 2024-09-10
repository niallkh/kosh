package kosh.app

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import kosh.ui.component.scaffold.LocalSnackbarHostOffset
import kosh.ui.component.scaffold.LocalSnackbarHostState
import kosh.ui.navigation.hosts.RootHost
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.stack.StackRouter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(
    onBackgroundColorChanged: (Color) -> Unit,
    stackRouter: StackRouter<RootRoute>,
) {
    KoshTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
            Scaffold(
                snackbarHost = {
                    val offsetY by LocalSnackbarHostOffset.current
                    SnackbarHost(
                        modifier = Modifier
                            .offset { IntOffset(x = 0, y = -offsetY.roundToPx()) }
                            .imePadding(),
                        hostState = snackbarHostState
                    )
                },
            ) { _ ->
                RootHost(stackRouter = stackRouter)
            }
        }

        val color = MaterialTheme.colorScheme.background
        LaunchedEffect(color) {
            onBackgroundColorChanged(color)
        }
    }
}
