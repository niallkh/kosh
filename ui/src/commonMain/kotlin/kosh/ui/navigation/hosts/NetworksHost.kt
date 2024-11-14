package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.NetworksRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.navigation.stack.popOr
import kosh.ui.navigation.stack.rememberStackRouter
import kosh.ui.network.DeleteNetworkScreen
import kosh.ui.network.NetworkScreen
import kosh.ui.network.NetworksScreen

@Composable
fun NetworksHost(
    link: NetworksRoute?,
    start: NetworksRoute = NetworksRoute.List,
    onResult: (RouteResult<NetworksRoute>) -> Unit,
) {
    val router = rememberStackRouter<NetworksRoute>({ start }, link)

    fun pop() {
        router.popOr { onResult(RouteResult.Finished()) }
    }

    fun navigateUp() {
        router.popOr { onResult(RouteResult.Up(start)) }
    }

    StackHost(router, ::pop) { route ->
        when (route) {
            is NetworksRoute.List -> NetworksScreen(
                onNavigateUp = ::navigateUp,
                onOpen = { router.pushNew(NetworksRoute.Details(it)) },
                onAdd = { router.pushNew(NetworksRoute.Details(null)) },
            )

            is NetworksRoute.Details -> NetworkScreen(
                id = route.id,
                onCancel = ::pop,
                onFinish = ::pop,
                onNavigateUp = ::navigateUp,
                onDelete = { router.replaceCurrent(NetworksRoute.Delete(it)) }
            )

            is NetworksRoute.Delete -> DeleteNetworkScreen(
                id = route.id,
                onFinish = { pop() }
            )
        }

        LogScreen(route)
    }
}
