package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import arrow.core.partially1
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.routes.TransactionsRoute
import kosh.ui.navigation.routes.wcRequestRoute
import kosh.ui.navigation.stack.StackHost
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
    onResult: (RouteResult<TransactionsRoute>) -> Unit,
) {
    StackHost(
        start = TransactionsRoute.List,
        link = link,
        onResult = { onResult(it) },
    ) { route ->
        when (route) {
            TransactionsRoute.List -> ActivityScreen(
                onOpenTransaction = { pushNew(TransactionsRoute.Details(it)) },
                onOpenRequest = { pushNew(wcRequestRoute(it)) },
                onOpenAuth = { pushNew(TransactionsRoute.Auth(it.id)) },
                onOpenProposal = { pushNew(TransactionsRoute.Proposal(it.id, it.requestId)) },
                onOpenNetworks = onOpen.partially1(RootRoute.Networks()),
                onOpenWallets = onOpen.partially1(RootRoute.Wallets()),
            )

            is TransactionsRoute.Details -> TransactionScreen(
                id = route.id,
                onNavigateUp = { navigateUp() },
                onDelete = { replaceCurrent(TransactionsRoute.Delete(it)) },
                onOpen = onOpen
            )

            is TransactionsRoute.Delete -> DeleteTransactionScreen(
                id = route.id,
                onFinish = { result() }
            )

            is TransactionsRoute.Proposal -> WcProposalScreen(
                id = route.id,
                requestId = route.requestId,
                onResult = { result(it?.value) },
                onCancel = { pop() },
                onNavigateUp = { navigateUp() }
            )

            is TransactionsRoute.Auth -> WcAuthenticationScreen(
                id = route.id,
                onResult = { result() },
                onCancel = { pop() },
                onNavigateUp = { navigateUp() }
            )

            is TransactionsRoute.Request -> WcRequestScreen(
                id = route.id,
                onResult = { replaceCurrent(wcRequestRoute(it)) },
                onNavigateUp = { navigateUp() }
            )

            is TransactionsRoute.PersonalSign -> WcSignPersonalScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { result() },
                onNavigateUp = { navigateUp() }
            )

            is TransactionsRoute.SignTypedData -> WcSignTypedScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { result() },
                onNavigateUp = { navigateUp() }
            )

            is TransactionsRoute.SendTransaction -> WcSendTransactionScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { result() },
                onNavigateUp = { navigateUp() },
                onOpen = onOpen,
            )

            is TransactionsRoute.AddEthereumNetwork -> WcAddNetworkScreen(
                id = route.id,
                onCancel = { pop() },
                onResult = { result() },
                onNavigateUp = { navigateUp() }
            )

            is TransactionsRoute.WatchToken -> WcWatchTokenScreen(
                id = route.id,
                chainAddress = route.chainAddress,
                onResult = { result() },
                onNft = { replaceCurrent(TransactionsRoute.WatchNft(route.id, it, null)) },
                onNavigateUp = { navigateUp() }
            )

            is TransactionsRoute.WatchNft -> WcWatchNftScreen(
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
