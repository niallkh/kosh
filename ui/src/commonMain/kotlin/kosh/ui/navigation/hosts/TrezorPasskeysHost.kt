package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.TrezorPasskeysRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.navigation.stack.popOr
import kosh.ui.navigation.stack.rememberStackRouter
import kosh.ui.trezor.TrezorPasskeysScreen

@Composable
fun TrezorPasskeysHost(
    link: TrezorPasskeysRoute?,
    start: TrezorPasskeysRoute = TrezorPasskeysRoute.List,
    onResult: (RouteResult<TrezorPasskeysRoute>) -> Unit,
) {
    val router = rememberStackRouter({ start }, link)

    fun pop() {
        router.popOr { onResult(RouteResult.Finished()) }
    }

    fun navigateUp() {
        router.popOr { onResult(RouteResult.Up(start)) }
    }

    StackHost(router, ::pop) { route ->
        when (route) {
            is TrezorPasskeysRoute.List -> {
                TrezorPasskeysScreen(
                    onNavigateUp = ::navigateUp
                )
            }
        }

        LogScreen(route)
    }
}
