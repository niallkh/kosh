package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.TransactionsRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.transaction.DeleteTransactionScreen
import kosh.ui.transaction.TransactionScreen


@Composable
fun TransactionsHost(
    link: TransactionsRoute,
    onResult: (RouteResult<TransactionsRoute>) -> Unit,
) {
    StackHost(
        link = link,
        onResult = { onResult(it) },
    ) { route ->
        when (route) {
            is TransactionsRoute.Details -> TransactionScreen(
                id = route.id,
                onNavigateUp = { navigateUp() },
                onDelete = { replaceCurrent(TransactionsRoute.Delete(it)) }
            )

            is TransactionsRoute.Delete -> DeleteTransactionScreen(
                id = route.id,
                onFinish = { finish() }
            )
        }

        LogScreen(route)
    }
}
