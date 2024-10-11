package kosh.ui.navigation.hosts

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.pushNew
import kosh.ui.analytics.LogScreen
import kosh.ui.navigation.RouteResult
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
        onResult = { onResult(it) },
    ) { route ->
        when (route) {
            is AddTokenRoute.Search -> SearchTokenScreen(
                address = route.address,
                onResult = { result() },
                onNft = { pushNew(AddTokenRoute.NftSearch(it)) },
                onNavigateUp = { navigateUp() },
            )

            is AddTokenRoute.NftSearch -> SearchNftScreen(
                chainAddress = route.chainAddress,
                tokenId = route.tokenId,
                onResult = { result() },
                onNavigateUp = { navigateUp() }
            )
        }

        LogScreen(route)
    }
}
