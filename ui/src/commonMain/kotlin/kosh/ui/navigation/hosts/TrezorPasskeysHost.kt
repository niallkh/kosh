package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.TrezorPasskeysRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.navigation.stack.rememberStackRouter
import kosh.ui.trezor.TrezorPasskeysScreen

@Composable
fun TrezorPasskeysHost(
    link: TrezorPasskeysRoute?,
    onResult: (RouteResult<TrezorPasskeysRoute>) -> Unit,
) {
    val stackRouter = rememberStackRouter(TrezorPasskeysRoute.List, link) {
        onResult(it)
    }

    StackHost(
        stackRouter = stackRouter,
    ) { route ->
        when (route) {
            is TrezorPasskeysRoute.List -> {
                TrezorPasskeysScreen(
                    onUp = { navigateUp() }
                )
            }
        }

        LogScreen(route)
    }
}
