package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import arrow.core.partially1
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.routes.WalletConnectRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.reown.WcAuthenticationScreen
import kosh.ui.reown.WcPairScreen
import kosh.ui.reown.WcProposalScreen
import kosh.ui.reown.WcSessionScreen
import kosh.ui.reown.WcSessionsScreen

@Composable
fun WalletConnectHost(
    link: WalletConnectRoute?,
    onOpen: (RootRoute) -> Unit,
    onResult: (RouteResult<WalletConnectRoute>) -> Unit,
) {
    StackHost(
        start = WalletConnectRoute.List,
        link = link,
        onResult = { onResult(it) },
    ) { route ->
        when (route) {
            WalletConnectRoute.List -> WcSessionsScreen(
                onOpen = { pushNew(WalletConnectRoute.Session(it.id)) },
                onOpenWallets = onOpen.partially1(RootRoute.Wallets()),
                onOpenNetworks = onOpen.partially1(RootRoute.Networks()),
                onPair = { pushNew(WalletConnectRoute.Pair()) }
            )

            is WalletConnectRoute.Session -> WcSessionScreen(
                id = route.id,
                onCancel = { pop() },
                onNavigateUp = { navigateUp() },
                onFinish = { result() }
            )

            is WalletConnectRoute.Pair -> WcPairScreen(
                initial = route.uri,
                onCancel = { pop() },
                onProposal = { replaceCurrent(WalletConnectRoute.Proposal(it.id, it.requestId)) },
                onAuthenticate = { replaceCurrent(WalletConnectRoute.Auth(it.id)) },
                onNavigateUp = { navigateUp() }
            )

            is WalletConnectRoute.Proposal -> WcProposalScreen(
                id = route.id,
                requestId = route.requestId,
                onResult = { result(it?.value) },
                onCancel = { pop() },
                onNavigateUp = { navigateUp() }
            )

            is WalletConnectRoute.Auth -> WcAuthenticationScreen(
                id = route.id,
                onResult = { result() },
                onCancel = { pop() },
                onNavigateUp = { navigateUp() }
            )
        }

        LogScreen(route)
    }
}

