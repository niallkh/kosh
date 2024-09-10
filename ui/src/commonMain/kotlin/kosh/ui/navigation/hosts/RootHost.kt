package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.push
import kosh.ui.navigation.animation.materialStackAnimation
import kosh.ui.navigation.animation.sharedAxisX
import kosh.ui.navigation.animation.sharedAxisY
import kosh.ui.navigation.animation.sharedAxisZ
import kosh.ui.navigation.hosts.wc.WcProposalHost
import kosh.ui.navigation.hosts.wc.WcRequestHost
import kosh.ui.navigation.hosts.wc.WcSessionsHost
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
        animation = materialStackAnimation {
            when (it) {
                is RootRoute.Tokens -> if (it.isNft) sharedAxisZ() else sharedAxisY()
                is RootRoute.Transactions -> sharedAxisY()
                is RootRoute.AddToken -> sharedAxisY()
                is RootRoute.WcSession -> sharedAxisY()
                else -> sharedAxisX()
            }
        }
    ) { route ->
        when (route) {
            is RootRoute.Home -> HomeHost(
                link = route.link,
                onOpen = { push(it) },
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

            is RootRoute.WcSession -> WcSessionsHost(
                link = route.link,
                onResult = { pop(it, RootRoute::WcSession) },
            )

            is RootRoute.WcProposal -> WcProposalHost(
                link = route.link,
                onResult = { pop(it, RootRoute::WcProposal) }
            )

            is RootRoute.WcRequest -> WcRequestHost(
                link = route.link,
                onResult = { pop(it, RootRoute::WcRequest) },
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
