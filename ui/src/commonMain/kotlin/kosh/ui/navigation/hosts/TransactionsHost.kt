package kosh.ui.navigation.hosts

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.component.scaffold.LocalSnackbarHostState
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
import kosh.ui.resources.icons.Networks
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
            TransactionsRoute.List -> {
                val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    topBar = {
                        LargeTopAppBar(
                            scrollBehavior = scrollBehavior,
                            title = { Text("Activity") },
                            actions = {
                                IconButton(onClick = {
                                    onOpen(RootRoute.Networks())
                                }) {
                                    Icon(Networks, "Networks")
                                }

                                IconButton(onClick = {
                                    onOpen(RootRoute.Wallets())
                                }) {
                                    Icon(Icons.Outlined.AccountBalanceWallet, "Accounts")
                                }
                            }
                        )
                    },
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { paddingValues ->
                    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
                        ActivityScreen(
                            contentPading = paddingValues,
                            scrollBehavior = scrollBehavior,
                            onOpenTransaction = {
                                pushNew(TransactionsRoute.Details(it))
                            },
                            onOpenRequest = {
                                pushNew(wcRequestRoute(it))
                            },
                            onOpenAuth = {
                                pushNew(TransactionsRoute.Auth(it.id))
                            },
                            onOpenProposal = {
                                pushNew(TransactionsRoute.Proposal(it.id, it.requestId))
                            }
                        )
                    }
                }
            }

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
