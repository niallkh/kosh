package kosh.ui.navigation.hosts.wc

import androidx.compose.runtime.Composable
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.wc.WcSessionRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.wc.WcSessionScreen

@Composable
fun WcSessionsHost(
    link: WcSessionRoute,
    onResult: (RouteResult<WcSessionRoute>) -> Unit,
) {
    StackHost(
        link = link,
        onResult = onResult,
    ) { route ->
        when (route) {
            is WcSessionRoute.Session -> WcSessionScreen(
                id = route.id,
                onCancel = { pop() },
                onNavigateUp = { navigateUp() },
                onFinish = { finish() }
            )
        }

        LogScreen(route)
    }
}
