package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.pushNew
import kosh.ui.navigation.pop
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.navigation.stack.StackRouter

@Composable
fun RootHost(
    stackRouter: StackRouter<RootRoute>,
) {
    StackHost(
        stackRouter = stackRouter,
    ) { route ->
        when (route) {
            is RootRoute.Home -> HomeHost(
                link = route.link,
                onOpen = { pushNew(it) },
                onResult = { pop(it, RootRoute::Home) },
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

            is RootRoute.WcSessions -> WcSessionsHost(
                link = route.link,
                onResult = { pop(it, RootRoute::WcSessions) },
            )

            is RootRoute.Transactions -> TransactionsHost(
                link = route.link,
                onResult = { pop(it, RootRoute::Transactions) }
            )

            is RootRoute.Tokens -> TokensHost(
                link = route.link,
                onResult = { pop(it, RootRoute::Tokens) }
            )
        }
    }
}
