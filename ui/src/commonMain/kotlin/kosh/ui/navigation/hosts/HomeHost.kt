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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.replaceAll
import kosh.ui.analytics.LogScreen
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.scaffold.ProvideSnackbarOffset
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.animation.fadeThroughIn
import kosh.ui.navigation.animation.fadeThroughOut
import kosh.ui.navigation.animation.stackAnimationFadeThrough
import kosh.ui.navigation.routes.HomeRoute
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.routes.TokensRoute
import kosh.ui.navigation.routes.TransactionsRoute
import kosh.ui.navigation.routes.wc.WcProposalRoute
import kosh.ui.navigation.routes.wc.WcSessionRoute
import kosh.ui.navigation.routes.wc.wcRequestRoute
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

    KoshScaffold(
        title = {
            AnimatedContent(
                activeRoute.title,
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
        },
        bottomBar = {
            NavigationBar(
                items = listOf(HomeRoute.Assets, HomeRoute.Activity, HomeRoute.WalletConnect),
                navigation = stackRouter,
                activeRoute = activeRoute,
                start = start,
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
                                    RootRoute.WcProposal(WcProposalRoute.Pair())
                                )
                            }
                        },
                    ) {
                        Icon(icon, contentDescription = "fab")
                    }
                } ?: Spacer(Modifier.size(56.dp))
            }
        },
        onNavigateUp = null,
    ) { paddingValues ->

        StackHost(
            stackRouter = stackRouter,
            animation = stackAnimationFadeThrough(),
        ) { route ->

            when (route) {
                HomeRoute.Assets -> AssetsScreen(
                    paddingValues = paddingValues,
                    open = { token, isNft ->
                        onOpen(RootRoute.Tokens(TokensRoute.Details(token), isNft))
                    },
                )

                HomeRoute.Activity -> ActivityScreen(
                    paddingValues = paddingValues,
                    onOpenTransaction = {
                        onOpen(RootRoute.Transactions(TransactionsRoute.Details(it)))
                    },
                    onOpenRequest = {
                        onOpen(RootRoute.WcRequest(wcRequestRoute(it)))
                    },
                    onOpenAuth = {
                        onOpen(RootRoute.WcProposal(WcProposalRoute.Auth(it.id)))
                    },
                    onOpenProposal = {
                        onOpen(RootRoute.WcProposal(WcProposalRoute.Proposal(it.id, it.requestId)))
                    }
                )

                HomeRoute.WalletConnect -> WcSessionsScreen(
                    paddingValues = paddingValues,
                    onOpen = { onOpen(RootRoute.WcSession(WcSessionRoute.Session(it.id))) },
                )
            }

            LogScreen(route)
        }
    }
}

@Composable
private fun NavigationBar(
    start: HomeRoute,
    activeRoute: HomeRoute,
    items: List<HomeRoute>,
    navigation: StackNavigation<HomeRoute>,
) {
    NavigationBar {
        items.forEach { item ->
            val selected = item == activeRoute

            NavigationBarItem(
                icon = { Icon(item.icon, null) },
                label = { Text(item.label) },
                selected = selected,
                onClick = {
                    when {
                        selected -> Unit
                        start == item -> navigation.replaceAll(start)
                        else -> navigation.replaceAll(start, item)
                    }
                },
            )
        }
    }

    ProvideSnackbarOffset(80.dp)
}
