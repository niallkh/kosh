package kosh.ui.navigation.hosts.wc

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.wc.WcRequestRoute
import kosh.ui.navigation.routes.wc.wcRequestRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.wc.WcAddNetworkScreen
import kosh.ui.wc.WcRequestScreen
import kosh.ui.wc.WcSendTransactionScreen
import kosh.ui.wc.WcSignPersonalScreen
import kosh.ui.wc.WcSignTypedScreen
import kosh.ui.wc.WcWatchNftScreen
import kosh.ui.wc.WcWatchTokenScreen

@Composable
fun WcRequestHost(
    link: WcRequestRoute,
    onResult: (RouteResult<WcRequestRoute>) -> Unit,
) {
    StackHost(
        link = link,
        onResult = onResult,
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
                onResult = { finish() },
                onNavigateUp = { navigateUp() }
            )

            is WcRequestRoute.SignTypedData -> WcSignTypedScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { finish() },
                onNavigateUp = { navigateUp() }
            )

            is WcRequestRoute.SendTransaction -> WcSendTransactionScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { finish() },
                onNavigateUp = { navigateUp() }
            )

            is WcRequestRoute.AddEthereumNetwork -> WcAddNetworkScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { finish() },
                onNavigateUp = { navigateUp() }
            )

            is WcRequestRoute.WatchToken -> WcWatchTokenScreen(
                id = route.id,
                chainAddress = route.chainAddress,
                onResult = { finish() },
                onNft = { replaceCurrent(WcRequestRoute.WatchNft(route.id, it, null)) },
                onNavigateUp = { navigateUp() }
            )

            is WcRequestRoute.WatchNft -> WcWatchNftScreen(
                id = route.id,
                chainAddress = route.chainAddress,
                tokenId = route.tokenId,
                onResult = { finish() },
                onNavigateUp = { navigateUp() }
            )
        }

        LogScreen(route)
    }
}
