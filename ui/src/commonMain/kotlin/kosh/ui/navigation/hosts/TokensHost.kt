package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.animation.stackAnimationSharedAxisX
import kosh.ui.navigation.routes.TokensRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.token.DeleteTokenScreen
import kosh.ui.token.TokenScreen

@Composable
fun TokensHost(
    link: TokensRoute,
    onResult: (RouteResult<TokensRoute>) -> Unit,
) {
    StackHost(
        link = link,
        onResult = onResult,
        animation = stackAnimationSharedAxisX(),
    ) { route ->
        when (route) {
            is TokensRoute.Details -> TokenScreen(
                id = route.id,
                onNavigateUp = { navigateUp() },
                onDelete = { replaceCurrent(TokensRoute.Delete(it)) }
            )

            is TokensRoute.Delete -> DeleteTokenScreen(
                id = route.id,
                onFinish = { pop() }
            )
        }

        LogScreen(route)
    }
}
