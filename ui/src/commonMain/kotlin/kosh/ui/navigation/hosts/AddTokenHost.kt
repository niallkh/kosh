package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.push
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.animation.stackAnimationSharedAxisX
import kosh.ui.navigation.routes.AddTokenRoute
import kosh.ui.navigation.stack.StackHost
import kosh.ui.token.SearchNftScreen
import kosh.ui.token.SearchTokenScreen


@Composable
fun AddTokenHost(
    link: AddTokenRoute?,
    onResult: (RouteResult<AddTokenRoute>) -> Unit,
) {
    StackHost(
        start = AddTokenRoute.Search(),
        link = link,
        onResult = onResult,
        animation = stackAnimationSharedAxisX(),
    ) { route ->
        when (route) {
            is AddTokenRoute.Search -> SearchTokenScreen(
                address = route.address,
                onResult = { finish() },
                onNft = { push(AddTokenRoute.NftSearch(it)) },
                onNavigateUp = { navigateUp() },
            )

            is AddTokenRoute.NftSearch -> SearchNftScreen(
                chainAddress = route.chainAddress,
                tokenId = route.tokenId,
                onResult = { finish() },
                onNavigateUp = { navigateUp() }
            )
        }

        LogScreen(route)
    }
}