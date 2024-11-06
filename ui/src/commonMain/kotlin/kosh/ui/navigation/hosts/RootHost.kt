package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.hosts.home.HandleHomeTabReset
import kosh.ui.navigation.pop
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.navigation.stack.rememberStackRouter
import kotlinx.serialization.serializer

@Composable
fun RootHost(
    start: () -> RootRoute,
    link: RootRoute?,
    onResult: @DisallowComposableCalls (RouteResult.Finished) -> Unit,
) {
    val stackRouter = rememberStackRouter<RootRoute>(
        start = start,
        link = link,
        onResult = {
            when (it) {
                is RouteResult.Finished -> onResult(it)
                is RouteResult.Up -> when (val route = it.route) {
                    null -> replaceAll(start())
                    else -> replaceAll(start(), route)
                }
            }
        },
        serializer = serializer()
    )

    HandleHomeTabReset {
        stackRouter.replaceAll(start())
    }

    StackHost(
        stackRouter = stackRouter,
    ) { route ->

        when (route) {
            is RootRoute.Tokens -> TokensHost(
                link = route.link,
                onOpen = { pushNew(it) },
                onAddToken = { pushNew(RootRoute.AddToken()) },
                onResult = { pop(it, RootRoute::Tokens) }
            )

            is RootRoute.Transactions -> TransactionsHost(
                link = route.link,
                onOpen = { pushNew(it) },
                onResult = { pop(it, RootRoute::Transactions) }
            )

            is RootRoute.WalletConnect -> WalletConnectHost(
                link = route.link,
                onOpen = { pushNew(it) },
                onResult = { pop(it, RootRoute::WalletConnect) },
            )

            is RootRoute.Wallets -> WalletsHost(
                link = route.link,
                onResult = { pop(it, RootRoute::Wallets) },
            )

            is RootRoute.Networks -> NetworksHost(
                link = route.link,
                onResult = { pop(it, RootRoute::Networks) },
            )

            is RootRoute.AddToken -> AddTokenHost(
                link = route.link,
                onResult = { pop(it, RootRoute::AddToken) }
            )

            is RootRoute.TrezorPasskeys -> TrezorPasskeysHost(
                link = route.link,
                onResult = { pop(it, RootRoute::TrezorPasskeys) }
            )
        }
    }
}
