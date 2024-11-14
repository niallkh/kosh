package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.domain.models.hw.Keystone
import kosh.domain.models.hw.Ledger
import kosh.domain.models.hw.Trezor
import kosh.ui.account.AccountScreen
import kosh.ui.account.DeleteAccountScreen
import kosh.ui.account.DiscoveryAccountTokensScreen
import kosh.ui.analytics.LogScreen
import kosh.ui.keystone.NewKeystoneAccountScreen
import kosh.ui.ledger.NewLedgerAccountScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.WalletsRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.navigation.stack.popOr
import kosh.ui.navigation.stack.rememberStackRouter
import kosh.ui.trezor.NewTrezorAccountScreen
import kosh.ui.wallet.WalletsScreen

@Composable
fun WalletsHost(
    link: WalletsRoute?,
    start: WalletsRoute = WalletsRoute.List,
    onResult: (RouteResult<WalletsRoute>) -> Unit,
) {
    val router = rememberStackRouter<WalletsRoute>({ start }, link)

    fun pop() {
        router.popOr { onResult(RouteResult.Finished()) }
    }

    fun navigateUp() {
        router.popOr { onResult(RouteResult.Up(start)) }
    }

    StackHost(router, ::pop) { route ->
        when (route) {
            is WalletsRoute.List -> WalletsScreen(
                onNavigateUp = ::navigateUp,
                onOpen = { router.pushNew(WalletsRoute.Account(it)) },
                onAdd = { router.pushNew(WalletsRoute.NewAccount(it)) }
            )

            is WalletsRoute.Account -> AccountScreen(
                id = route.id,
                onCancel = ::pop,
                onNavigateUp = ::navigateUp,
                onDelete = { router.replaceCurrent(WalletsRoute.DeleteAccount(it)) }
            )

            is WalletsRoute.DeleteAccount -> DeleteAccountScreen(
                id = route.id,
                onFinish = ::pop
            )

            is WalletsRoute.NewAccount -> when (route.hw) {
                is Trezor -> NewTrezorAccountScreen(
                    trezor = route.hw,
                    onFinish = { router.replaceCurrent(WalletsRoute.TokensDiscovery(it)) },
                    onNavigateUp = ::navigateUp,
                )

                is Ledger -> NewLedgerAccountScreen(
                    ledger = route.hw,
                    onFinish = { router.replaceCurrent(WalletsRoute.TokensDiscovery(it)) },
                    onNavigateUp = ::navigateUp,
                )

                is Keystone -> NewKeystoneAccountScreen(
                    keystone = route.hw,
                    onFinish = { router.replaceCurrent(WalletsRoute.TokensDiscovery(it)) },
                    onNavigateUp = ::navigateUp,
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
