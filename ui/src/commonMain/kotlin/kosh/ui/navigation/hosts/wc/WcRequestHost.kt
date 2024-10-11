package kosh.ui.navigation.hosts.wc

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.wc.WcRequestRoute
import kosh.ui.navigation.routes.wc.wcRequestRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.reown.WcAddNetworkScreen
import kosh.ui.reown.WcRequestScreen
import kosh.ui.reown.WcSendTransactionScreen
import kosh.ui.reown.WcSignPersonalScreen
import kosh.ui.reown.WcSignTypedScreen
import kosh.ui.reown.WcWatchNftScreen
import kosh.ui.reown.WcWatchTokenScreen

@Composable
fun WcRequestHost(
    link: WcRequestRoute,
    onResult: (RouteResult<WcRequestRoute>) -> Unit,
) {
    StackHost(
        link = link,
        onResult = { onResult(it) },
    ) { route ->
        when (route) {
            is WcRequestRoute.Request -> WcRequestScreen(
                id = route.id,
                onResult = { replaceCurrent(wcRequestRoute(it)) },
                onNavigateUp = { navigateUp() }
            )

            is WcRequestRoute.PersonalSign -> WcSignPersonalScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { result() },
                onNavigateUp = { navigateUp() }
            )

            is WcRequestRoute.SignTypedData -> WcSignTypedScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { result() },
                onNavigateUp = { navigateUp() }
            )

            is WcRequestRoute.SendTransaction -> WcSendTransactionScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { result() },
                onNavigateUp = { navigateUp() }
            )

            is WcRequestRoute.AddEthereumNetwork -> WcAddNetworkScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { result() },
                onNavigateUp = { navigateUp() }
            )

            is WcRequestRoute.WatchToken -> WcWatchTokenScreen(
                id = route.id,
                chainAddress = route.chainAddress,
                onResult = { result() },
                onNft = { replaceCurrent(WcRequestRoute.WatchNft(route.id, it, null)) },
                onNavigateUp = { navigateUp() }
            )

            is WcRequestRoute.WatchNft -> WcWatchNftScreen(
                id = route.id,
                chainAddress = route.chainAddress,
                tokenId = route.tokenId,
                onResult = { result() },
                onNavigateUp = { navigateUp() }
            )
        }

        LogScreen(route)
    }
}
