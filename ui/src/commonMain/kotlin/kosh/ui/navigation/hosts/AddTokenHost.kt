package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.replaceCurrent
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.AddTokenRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.navigation.stack.popOr
import kosh.ui.navigation.stack.rememberStackRouter
import kosh.ui.token.SearchNftScreen
import kosh.ui.token.SearchTokenScreen


@Composable
fun AddTokenHost(
    link: AddTokenRoute?,
    start: () -> AddTokenRoute = { AddTokenRoute.Search() },
    onResult: (RouteResult<AddTokenRoute>) -> Unit,
) {
    val router = rememberStackRouter<AddTokenRoute>(start, link)

    fun pop() {
        router.popOr { onResult(RouteResult.Finished()) }
    }

    fun finish(redirect: String? = null) {
        router.popOr { onResult(RouteResult.Finished(redirect)) }
    }

    fun navigateUp() {
        router.popOr { onResult(RouteResult.Up(start())) }
    }

    StackHost(router, ::pop) { route ->
        when (route) {
            is AddTokenRoute.Search -> SearchTokenScreen(
                address = route.address,
                onFinish = ::finish,
                onNft = { router.replaceCurrent(AddTokenRoute.NftSearch(it)) },
                onNavigateUp = { navigateUp() },
            )

            is AddTokenRoute.NftSearch -> SearchNftScreen(
                chainAddress = route.chainAddress,
                tokenId = route.tokenId,
                onFinish = ::finish,
                onNavigateUp = { navigateUp() }
            )
        }

        LogScreen(route)
    }
}
