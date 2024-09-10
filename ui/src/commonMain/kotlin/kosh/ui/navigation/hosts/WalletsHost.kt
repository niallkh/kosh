package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.domain.entities.WalletEntity
import kosh.ui.account.AccountScreen
import kosh.ui.account.DeleteAccountScreen
import kosh.ui.account.WalletsScreen
import kosh.ui.analytics.LogScreen
import kosh.ui.ledger.NewLedgerAccountScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.animation.stackAnimationSharedAxisY
import kosh.ui.navigation.routes.WalletsRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.trezor.NewTrezorAccountScreen

@Composable
fun WalletsHost(
    link: WalletsRoute?,
    onResult: (RouteResult<WalletsRoute>) -> Unit,
) {
    StackHost(
        start = WalletsRoute.List,
        link = link,
        onResult = onResult,
        animation = stackAnimationSharedAxisY(),
    ) { route ->
        when (route) {
            is WalletsRoute.List -> WalletsScreen(
                onNavigateUp = { navigateUp() },
                onOpen = { push(WalletsRoute.Account(it)) },
                onAdd = { type ->
                    when (type) {
                        WalletEntity.Type.Trezor -> push(WalletsRoute.NewTrezorAccount())
                        WalletEntity.Type.Ledger -> push(WalletsRoute.NewLedgerAccount())
                    }
                }
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

            is WalletsRoute.NewTrezorAccount -> NewTrezorAccountScreen(
                onFinish = { pop() },
                onNavigateUp = { navigateUp() },
            )

            is WalletsRoute.NewLedgerAccount -> NewLedgerAccountScreen(
                onResult = { pop() },
                onNavigateUp = { navigateUp() }
            )
        }

        LogScreen(route)
    }
}
