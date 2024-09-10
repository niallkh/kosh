package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.animation.stackAnimationSharedAxisY
import kosh.ui.navigation.routes.NetworksRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.network.DeleteNetworkScreen
import kosh.ui.network.NetworkScreen
import kosh.ui.network.NetworksScreen

@Composable
fun NetworksHost(
    link: NetworksRoute?,
    onResult: (RouteResult<NetworksRoute>) -> Unit,
) {
    StackHost(
        start = NetworksRoute.List,
        link = link,
        onResult = onResult,
        animation = stackAnimationSharedAxisY(),
    ) { route ->

        when (route) {
            is NetworksRoute.List -> NetworksScreen(
                onNavigateUp = { navigateUp() },
                onOpen = { push(NetworksRoute.Details(it)) },
                onAdd = { push(NetworksRoute.Details(null)) },
            )

            is NetworksRoute.Details -> NetworkScreen(
                id = route.id,
                onCancel = { pop() },
                onFinish = { pop() },
                onNavigateUp = { navigateUp() },
                onDelete = { replaceCurrent(NetworksRoute.Delete(it)) }
            )

            is NetworksRoute.Delete -> DeleteNetworkScreen(
                id = route.id,
                onFinish = { pop() }
            )
        }

        LogScreen(route)
    }
}
