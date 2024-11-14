package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import arrow.core.compose
import arrow.core.partially1
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.routes.WalletConnectRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.navigation.stack.popOr
import kosh.ui.navigation.stack.rememberStackRouter
import kosh.ui.reown.WcAuthenticationScreen
import kosh.ui.reown.WcPairScreen
import kosh.ui.reown.WcProposalScreen
import kosh.ui.reown.WcSessionScreen
import kosh.ui.reown.WcSessionsScreen

@Composable
fun WalletConnectHost(
    link: WalletConnectRoute?,
    onOpen: (RootRoute) -> Unit,
    start: WalletConnectRoute = WalletConnectRoute.List,
    onResult: (RouteResult<WalletConnectRoute>) -> Unit,
) {
    val router = rememberStackRouter<WalletConnectRoute>({ start }, link)

    fun pop() {
        router.popOr { onResult(RouteResult.Finished()) }
    }

    fun finish(redirect: String? = null) {
        router.popOr { onResult(RouteResult.Finished(redirect)) }
    }

    fun navigateUp() {
        router.popOr { onResult(RouteResult.Up(start)) }
    }
    StackHost(router, ::pop) { route ->
        when (route) {
            WalletConnectRoute.List -> WcSessionsScreen(
                onOpen = { router.pushNew(WalletConnectRoute.Session(it.id)) },
                onOpenWallets = onOpen.partially1(RootRoute.Wallets()),
                onOpenNetworks = onOpen.partially1(RootRoute.Networks()),
                onPair = { router.pushNew(WalletConnectRoute.Pair()) }
            )

            is WalletConnectRoute.Session -> WcSessionScreen(
                id = route.id,
                onCancel = ::pop,
                onNavigateUp = ::navigateUp,
                onFinish = ::finish
            )

            is WalletConnectRoute.Pair -> WcPairScreen(
                initial = route.uri,
                onCancel = ::pop,
                onProposal = {
                    router.replaceCurrent(WalletConnectRoute.Proposal(it.id, it.requestId))
                },
                onAuthenticate = {
                    router.replaceCurrent(WalletConnectRoute.Auth(it.id))
                },
                onNavigateUp = ::navigateUp
            )

            is WalletConnectRoute.Proposal -> WcProposalScreen(
                id = route.id,
                requestId = route.requestId,
                onFinish = ::finish compose { it?.value },
                onCancel = ::pop,
                onNavigateUp = ::navigateUp
            )

            is WalletConnectRoute.Auth -> WcAuthenticationScreen(
                id = route.id,
                onFinish = ::finish,
                onCancel = ::pop,
                onNavigateUp = ::navigateUp
            )
        }

        LogScreen(route)
    }
}

