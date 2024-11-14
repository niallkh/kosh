package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import com.arkivanov.decompose.router.stack.pushNew
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.navigation.stack.pop
import kosh.ui.navigation.stack.popOr
import kosh.ui.navigation.stack.rememberStackRouter

@Composable
fun RootHost(
    start: () -> RootRoute,
    link: RootRoute?,
    onResult: @DisallowComposableCalls (RouteResult.Finished) -> Unit,
) {
    val router = rememberStackRouter<RootRoute>(start, link)

    fun handle(result: RouteResult<RootRoute>) {
        when (result) {
            is RouteResult.Finished -> onResult(result)
            is RouteResult.Up -> router.reset(result.route)
        }
    }

    fun finish() {
        router.popOr { onResult(RouteResult.Finished()) }
    }

    StackHost(router, ::finish) { route ->

        when (route) {
            is RootRoute.Tokens -> TokensHost(
                link = route.link,
                onOpen = { router.pushNew(it) },
                onAddToken = { router.pushNew(RootRoute.AddToken()) },
                onResult = { router.pop(it, RootRoute::Tokens, ::handle) },
            )

            is RootRoute.Transactions -> TransactionsHost(
                link = route.link,
                onOpen = { router.pushNew(it) },
                onResult = { router.pop(it, RootRoute::Transactions, ::handle) },
            )

            is RootRoute.WalletConnect -> WalletConnectHost(
                link = route.link,
                onOpen = { router.pushNew(it) },
                onResult = { router.pop(it, RootRoute::WalletConnect, ::handle) },
            )

            is RootRoute.Wallets -> WalletsHost(
                link = route.link,
                onResult = { router.pop(it, RootRoute::Wallets, ::handle) },
            )

            is RootRoute.Networks -> NetworksHost(
                link = route.link,
                onResult = { router.pop(it, RootRoute::Networks, ::handle) },
            )

            is RootRoute.AddToken -> AddTokenHost(
                link = route.link,
                onResult = { router.pop(it, RootRoute::AddToken, ::handle) },
            )

            is RootRoute.TrezorPasskeys -> TrezorPasskeysHost(
                link = route.link,
                onResult = { router.pop(it, RootRoute::TrezorPasskeys, ::handle) },
            )
        }
    }
}
