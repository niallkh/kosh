package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.domain.models.hw.Ledger
import kosh.domain.models.hw.Trezor
import kosh.ui.account.AccountScreen
import kosh.ui.account.DeleteAccountScreen
import kosh.ui.account.DiscoveryAccountTokensScreen
import kosh.ui.analytics.LogScreen
import kosh.ui.ledger.NewLedgerAccountScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.animation.materialStackAnimation
import kosh.ui.navigation.animation.sharedAxisX
import kosh.ui.navigation.animation.sharedAxisY
import kosh.ui.navigation.routes.WalletsRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.trezor.NewTrezorAccountScreen
import kosh.ui.wallet.WalletsScreen

@Composable
fun WalletsHost(
    link: WalletsRoute?,
    onResult: (RouteResult<WalletsRoute>) -> Unit,
) {
    StackHost(
        start = WalletsRoute.List,
        link = link,
        onResult = onResult,
        animation = materialStackAnimation {
            when (it) {
                is WalletsRoute.Account -> sharedAxisY()
                else -> sharedAxisX()
            }
        },
    ) { route ->
        when (route) {
            is WalletsRoute.List -> WalletsScreen(
                onNavigateUp = { navigateUp() },
                onOpen = { push(WalletsRoute.Account(it)) },
                onAdd = { hw -> push(WalletsRoute.NewAccount(hw)) }
            )

            is WalletsRoute.Account -> AccountScreen(
                id = route.id,
                onCancel = { pop() },
                onNavigateUp = { navigateUp() },
                onDelete = { replaceCurrent(WalletsRoute.DeleteAccount(it)) }
            )

            is WalletsRoute.DeleteAccount -> DeleteAccountScreen(
                route.id,
                onFinish = { pop() }
            )

            is WalletsRoute.NewAccount -> when (route.hw) {
                is Trezor -> NewTrezorAccountScreen(
                    trezor = route.hw,
                    onFinish = { replaceCurrent(WalletsRoute.TokensDiscovery(it)) },
                    onNavigateUp = { navigateUp() },
                )

                is Ledger -> NewLedgerAccountScreen(
                    ledger = route.hw,
                    onFinish = { replaceCurrent(WalletsRoute.TokensDiscovery(it)) },
                    onNavigateUp = { navigateUp() },
                )
            }

            is WalletsRoute.TokensDiscovery -> DiscoveryAccountTokensScreen(
                id = route.id,
                onFinish = { pop() }
            )
        }

        LogScreen(route)
    }
}
