package kosh.ui.navigation.hosts.wc

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.wc.WcProposalRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.reown.WcAuthenticationScreen
import kosh.ui.reown.WcPairScreen
import kosh.ui.reown.WcProposalScreen

@Composable
fun WcProposalHost(
    link: WcProposalRoute,
    onResult: (RouteResult<WcProposalRoute>) -> Unit,
) {
    StackHost(
        link = link,
        onResult = { onResult(it) },
    ) { route ->
        when (route) {

            is WcProposalRoute.Pair -> WcPairScreen(
                initial = route.uri,
                onCancel = { pop() },
                onProposal = { replaceCurrent(WcProposalRoute.Proposal(it.id, it.requestId)) },
                onAuthenticate = { replaceCurrent(WcProposalRoute.Auth(it.id)) },
                onNavigateUp = { navigateUp() }
            )

            is WcProposalRoute.Proposal -> WcProposalScreen(
                id = route.id,
                requestId = route.requestId,
                onResult = { finish() },
                onCancel = { pop() },
                onNavigateUp = { navigateUp() }
            )

            is WcProposalRoute.Auth -> WcAuthenticationScreen(
                id = route.id,
                onResult = { finish() },
                onCancel = { pop() },
                onNavigateUp = { navigateUp() }
            )
        }

        LogScreen(route)
    }
}
