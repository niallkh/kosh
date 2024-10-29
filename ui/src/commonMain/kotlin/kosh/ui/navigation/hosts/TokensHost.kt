package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import arrow.core.partially1
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.routes.TokensRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.token.AssetsScreen
import kosh.ui.token.DeleteTokenScreen
import kosh.ui.token.TokenScreen

@Composable
fun TokensHost(
    link: TokensRoute?,
    onOpen: (RootRoute) -> Unit,
    onAddToken: () -> Unit,
    onResult: (RouteResult<TokensRoute>) -> Unit,
) {
    StackHost(
        start = TokensRoute.List,
        link = link,
        onResult = { onResult(it) },
    ) { route ->
        when (route) {
            TokensRoute.List -> AssetsScreen(
                onOpenToken = { token, _ -> pushNew(TokensRoute.Details(token)) },
                onAddToken = { onAddToken() },
                onOpenNetworks = onOpen.partially1(RootRoute.Networks()),
                onOpenWallets = onOpen.partially1(RootRoute.Wallets()),
            )

            is TokensRoute.Details -> TokenScreen(
                id = route.id,
                onNavigateUp = ::navigateUp,
                onDelete = { replaceCurrent(TokensRoute.Delete(it)) }
            )

            is TokensRoute.Delete -> DeleteTokenScreen(
                id = route.id,
                onFinish = { pop() }
            )
        }

        LogScreen(route)
    }
}
