package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import arrow.core.compose
import arrow.core.partially1
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.routes.TransactionsRoute
import kosh.ui.navigation.routes.wcRequestRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.navigation.stack.popOr
import kosh.ui.navigation.stack.rememberStackRouter
import kosh.ui.reown.WcAddNetworkScreen
import kosh.ui.reown.WcAuthenticationScreen
import kosh.ui.reown.WcProposalScreen
import kosh.ui.reown.WcRequestScreen
import kosh.ui.reown.WcSendTransactionScreen
import kosh.ui.reown.WcSignPersonalScreen
import kosh.ui.reown.WcSignTypedScreen
import kosh.ui.reown.WcWatchNftScreen
import kosh.ui.reown.WcWatchTokenScreen
import kosh.ui.transaction.ActivityScreen
import kosh.ui.transaction.DeleteTransactionScreen
import kosh.ui.transaction.TransactionScreen


@Composable
fun TransactionsHost(
    link: TransactionsRoute?,
    onOpen: (RootRoute) -> Unit,
    start: TransactionsRoute = TransactionsRoute.List,
    onResult: (RouteResult<TransactionsRoute>) -> Unit,
) {
    val router = rememberStackRouter<TransactionsRoute>({ start }, link)

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
            TransactionsRoute.List -> ActivityScreen(
                onOpenTransaction = { router.pushNew(TransactionsRoute.Details(it)) },
                onOpenRequest = { router.pushNew(wcRequestRoute(it)) },
                onOpenAuth = { router.pushNew(TransactionsRoute.Auth(it.id)) },
                onOpenProposal = {
                    router.pushNew(TransactionsRoute.Proposal(it.id, it.requestId))
                },
                onOpenNetworks = onOpen.partially1(RootRoute.Networks()),
                onOpenWallets = onOpen.partially1(RootRoute.Wallets()),
            )

            is TransactionsRoute.Details -> TransactionScreen(
                id = route.id,
                onNavigateUp = ::navigateUp,
                onDelete = { router.replaceCurrent(TransactionsRoute.Delete(it)) },
                onOpen = onOpen
            )

            is TransactionsRoute.Delete -> DeleteTransactionScreen(
                id = route.id,
                onFinish = ::finish
            )

            is TransactionsRoute.Proposal -> WcProposalScreen(
                id = route.id,
                requestId = route.requestId,
                onFinish = ::finish compose { it?.value },
                onCancel = ::pop,
                onNavigateUp = ::navigateUp
            )

            is TransactionsRoute.Auth -> WcAuthenticationScreen(
                id = route.id,
                onFinish = ::finish,
                onCancel = ::pop,
                onNavigateUp = ::navigateUp
            )

            is TransactionsRoute.Request -> WcRequestScreen(
                id = route.id,
                onFinish = { router.replaceCurrent(wcRequestRoute(it)) },
                onNavigateUp = ::navigateUp
            )

            is TransactionsRoute.PersonalSign -> WcSignPersonalScreen(
                id = route.id,
                onCancel = ::pop,
                onFinish = ::finish,
                onNavigateUp = ::navigateUp
            )

            is TransactionsRoute.SignTypedData -> WcSignTypedScreen(
                id = route.id,
                onCancel = ::pop,
                onFinish = ::finish,
                onNavigateUp = ::navigateUp
            )

            is TransactionsRoute.SendTransaction -> WcSendTransactionScreen(
                id = route.id,
                onCancel = ::pop,
                onFinish = ::finish,
                onNavigateUp = ::navigateUp,
                onOpen = onOpen,
            )

            is TransactionsRoute.AddEthereumNetwork -> WcAddNetworkScreen(
                id = route.id,
                onCancel = ::pop,
                onFinish = ::finish,
                onNavigateUp = ::navigateUp
            )

            is TransactionsRoute.WatchToken -> WcWatchTokenScreen(
                id = route.id,
                chainAddress = route.chainAddress,
                onFinish = ::finish,
                onNft = {
                    router.replaceCurrent(TransactionsRoute.WatchNft(route.id, it, null))
                },
                onNavigateUp = ::navigateUp
            )

            is TransactionsRoute.WatchNft -> WcWatchNftScreen(
                id = route.id,
                chainAddress = route.chainAddress,
                tokenId = route.tokenId,
                onFinish = ::finish,
                onNavigateUp = ::navigateUp
            )
        }

        LogScreen(route)
    }
}
