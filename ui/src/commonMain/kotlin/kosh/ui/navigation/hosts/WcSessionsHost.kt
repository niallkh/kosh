package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.WcSessionsRoute
import kosh.ui.navigation.routes.wcRequestRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.reown.WcAddNetworkScreen
import kosh.ui.reown.WcAuthenticationScreen
import kosh.ui.reown.WcPairScreen
import kosh.ui.reown.WcProposalScreen
import kosh.ui.reown.WcRequestScreen
import kosh.ui.reown.WcSendTransactionScreen
import kosh.ui.reown.WcSessionScreen
import kosh.ui.reown.WcSignPersonalScreen
import kosh.ui.reown.WcSignTypedScreen
import kosh.ui.reown.WcWatchNftScreen
import kosh.ui.reown.WcWatchTokenScreen

@Composable
fun WcSessionsHost(
    link: WcSessionsRoute,
    onResult: (RouteResult<WcSessionsRoute>) -> Unit,
) {
    StackHost(
        link = link,
        onResult = { onResult(it) },
    ) { route ->
        when (route) {
            is WcSessionsRoute.Session -> WcSessionScreen(
                id = route.id,
                onCancel = { pop() },
                onNavigateUp = { navigateUp() },
                onFinish = { result() }
            )

            is WcSessionsRoute.Pair -> WcPairScreen(
                initial = route.uri,
                onCancel = { pop() },
                onProposal = { replaceCurrent(WcSessionsRoute.Proposal(it.id, it.requestId)) },
                onAuthenticate = { replaceCurrent(WcSessionsRoute.Auth(it.id)) },
                onNavigateUp = { navigateUp() }
            )

            is WcSessionsRoute.Proposal -> WcProposalScreen(
                id = route.id,
                requestId = route.requestId,
                onResult = { result(it?.value) },
                onCancel = { pop() },
                onNavigateUp = { navigateUp() }
            )

            is WcSessionsRoute.Auth -> WcAuthenticationScreen(
                id = route.id,
                onResult = { result() },
                onCancel = { pop() },
                onNavigateUp = { navigateUp() }
            )

            is WcSessionsRoute.Request -> WcRequestScreen(
                id = route.id,
                onResult = { replaceCurrent(wcRequestRoute(it)) },
                onNavigateUp = { navigateUp() }
            )

            is WcSessionsRoute.PersonalSign -> WcSignPersonalScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { result() },
                onNavigateUp = { navigateUp() }
            )

            is WcSessionsRoute.SignTypedData -> WcSignTypedScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { result() },
                onNavigateUp = { navigateUp() }
            )

            is WcSessionsRoute.SendTransaction -> WcSendTransactionScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { result() },
                onNavigateUp = { navigateUp() }
            )

            is WcSessionsRoute.AddEthereumNetwork -> WcAddNetworkScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { result() },
                onNavigateUp = { navigateUp() }
            )

            is WcSessionsRoute.WatchToken -> WcWatchTokenScreen(
                id = route.id,
                chainAddress = route.chainAddress,
                onResult = { result() },
                onNft = { replaceCurrent(WcSessionsRoute.WatchNft(route.id, it, null)) },
                onNavigateUp = { navigateUp() }
            )

            is WcSessionsRoute.WatchNft -> WcWatchNftScreen(
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

