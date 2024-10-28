package kosh.ui.navigation.hosts

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.FloatingActionButton
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
import kosh.ui.navigation.routes.WalletConnectRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.reown.WcAuthenticationScreen
import kosh.ui.reown.WcPairScreen
import kosh.ui.reown.WcProposalScreen
import kosh.ui.reown.WcSessionScreen
import kosh.ui.reown.WcSessionsScreen
import kosh.ui.resources.icons.Networks

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
            WalletConnectRoute.List -> {
                val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    topBar = {
                        LargeTopAppBar(
                            scrollBehavior = scrollBehavior,
                            title = { Text("WalletConnect") },
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
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { pushNew(WalletConnectRoute.Pair()) },
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add Dapp")
                        }
                    },
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { paddingValues ->
                    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
                        WcSessionsScreen(
                            contentPadding = paddingValues,
                            scrollBehavior = scrollBehavior,
                            onOpen = { pushNew(WalletConnectRoute.Session(it.id)) },
                        )
                    }
                }
            }

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

