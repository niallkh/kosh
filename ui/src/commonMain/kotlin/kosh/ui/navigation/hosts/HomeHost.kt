package kosh.ui.navigation.hosts

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pushToFront
import kosh.ui.analytics.LogScreen
import kosh.ui.component.scaffold.LocalSnackbarHostState
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.animation.stackAnimationFadeThrough
import kosh.ui.navigation.animation.transitions.fadeThroughIn
import kosh.ui.navigation.animation.transitions.fadeThroughOut
import kosh.ui.navigation.routes.HomeRoute
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.routes.TokensRoute
import kosh.ui.navigation.routes.TransactionsRoute
import kosh.ui.navigation.routes.WcSessionsRoute
import kosh.ui.navigation.routes.wcRequestRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.navigation.stack.rememberStackRouter
import kosh.ui.reown.WcSessionsScreen
import kosh.ui.resources.icons.Networks
import kosh.ui.token.AssetsScreen
import kosh.ui.transaction.ActivityScreen

@Composable
fun HomeHost(
    link: HomeRoute? = null,
    onOpen: (RootRoute) -> Unit,
    onResult: (RouteResult<HomeRoute>) -> Unit,
) {
    val start = HomeRoute.Assets
    val stackRouter = rememberStackRouter(start, link) { onResult(it) }
    val childStackState by stackRouter.stack.subscribeAsState()
    val activeRoute by derivedStateOf { childStackState.active.configuration }
    val snackbarHostState = remember { SnackbarHostState() }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    AnimatedContent(
                        targetState = activeRoute.title,
                        transitionSpec = { fadeThroughIn() togetherWith fadeThroughOut() }
                    ) {
                        Box(Modifier.fillMaxWidth()) {
                            Text(it)
                        }
                    }
                },
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
        bottomBar = {
            NavigationBar(
                items = listOf(HomeRoute.Assets, HomeRoute.Activity, HomeRoute.WalletConnect),
                navigation = stackRouter,
                activeRoute = activeRoute,
            )
        },
        floatingActionButton = {
            AnimatedContent(
                targetState = activeRoute.fab,
                transitionSpec = {
                    scaleIn(animationSpec = tween(150)) togetherWith
                            scaleOut(tween(150, easing = FastOutLinearInEasing)) using
                            SizeTransform(clip = false)
                }
            ) { icon ->
                icon?.let {
                    FloatingActionButton(
                        onClick = {
                            when (activeRoute) {
                                HomeRoute.Assets -> onOpen(RootRoute.AddToken())

                                HomeRoute.Activity -> Unit

                                HomeRoute.WalletConnect -> onOpen(
                                    RootRoute.WcSessions(WcSessionsRoute.Pair())
                                )
                            }
                        },
                    ) {
                        Icon(icon, contentDescription = "fab")
                    }
                } ?: Spacer(Modifier.size(56.dp))
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
            StackHost(
                stackRouter = stackRouter,
                animation = stackAnimationFadeThrough(),
            ) { route ->

                when (route) {
                    HomeRoute.Assets -> AssetsScreen(
                        paddingValues = paddingValues,
                        scrollBehavior = scrollBehavior,
                        onOpenToken = { token, isNft ->
                            onOpen(RootRoute.Tokens(TokensRoute.Details(token), isNft))
                        },
                        onOpenNetworks = {
                            onOpen(RootRoute.Networks())
                        },
                        onOpenWallets = {
                            onOpen(RootRoute.Wallets())
                        }
                    )

                    HomeRoute.Activity -> ActivityScreen(
                        paddingValues = paddingValues,
                        scrollBehavior = scrollBehavior,
                        onOpenTransaction = {
                            onOpen(RootRoute.Transactions(TransactionsRoute.Details(it)))
                        },
                        onOpenRequest = {
                            onOpen(RootRoute.WcSessions(wcRequestRoute(it)))
                        },
                        onOpenAuth = {
                            onOpen(RootRoute.WcSessions(WcSessionsRoute.Auth(it.id)))
                        },
                        onOpenProposal = {
                            onOpen(
                                RootRoute.WcSessions(
                                    WcSessionsRoute.Proposal(it.id, it.requestId)
                                )
                            )
                        }
                    )

                    HomeRoute.WalletConnect -> WcSessionsScreen(
                        paddingValues = paddingValues,
                        scrollBehavior = scrollBehavior,
                        onOpen = { onOpen(RootRoute.WcSessions(WcSessionsRoute.Session(it.id))) },
                    )
                }

                LogScreen(route)
            }
        }
    }
}

@Composable
private fun NavigationBar(
    activeRoute: HomeRoute,
    items: List<HomeRoute>,
    navigation: StackNavigation<HomeRoute>,
) {
    NavigationBar {
        items.forEach { item ->
            val selected = item == activeRoute

            NavigationBarItem(
                icon = { Icon(item.icon, item.label) },
                label = { Text(item.label) },
                selected = selected,
                onClick = {
                    when {
                        selected -> Unit
                        else -> navigation.pushToFront(item)
                    }
                },
            )
        }
    }
}
