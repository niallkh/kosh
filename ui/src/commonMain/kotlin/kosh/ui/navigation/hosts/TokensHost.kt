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
import kosh.ui.navigation.stack.popOr
import kosh.ui.navigation.stack.rememberStackRouter
import kosh.ui.token.AssetsScreen
import kosh.ui.token.DeleteTokenScreen
import kosh.ui.token.TokenScreen

@Composable
fun TokensHost(
    link: TokensRoute?,
    onOpen: (RootRoute) -> Unit,
    onAddToken: () -> Unit,
    start: TokensRoute = TokensRoute.List,
    onResult: (RouteResult<TokensRoute>) -> Unit,
) {
    val router = rememberStackRouter<TokensRoute>({ start }, link)

    fun pop() {
        router.popOr { onResult(RouteResult.Finished()) }
    }

    fun navigateUp() {
        router.popOr { onResult(RouteResult.Up(start)) }
    }

    StackHost(
        stackRouter = router,
        onBack = ::pop,
    ) { route ->
        when (route) {
            TokensRoute.List -> AssetsScreen(
                onOpenToken = { token, _ -> router.pushNew(TokensRoute.Details(token)) },
                onAddToken = { onAddToken() },
                onOpenNetworks = onOpen.partially1(RootRoute.Networks()),
                onOpenWallets = onOpen.partially1(RootRoute.Wallets()),
            )

            is TokensRoute.Details -> TokenScreen(
                id = route.id,
                onNavigateUp = ::navigateUp,
                onDelete = { router.replaceCurrent(TokensRoute.Delete(it)) }
            )

            is TokensRoute.Delete -> DeleteTokenScreen(
                id = route.id,
                onFinish = ::pop
            )
        }

        LogScreen(route)
    }
}

