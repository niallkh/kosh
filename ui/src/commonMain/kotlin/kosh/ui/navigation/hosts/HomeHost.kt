package kosh.ui.navigation.hosts

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.navigate
import com.arkivanov.decompose.router.pages.select
import kosh.presentation.component.selector.selector
import kosh.ui.generated.resources.Res
import kosh.ui.generated.resources.activity_title
import kosh.ui.generated.resources.assets_title
import kosh.ui.generated.resources.wc_title
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.pages.PagesHost
import kosh.ui.navigation.pages.rememberPagesRouter
import kosh.ui.navigation.routes.HomeRoute
import kosh.ui.navigation.routes.HomeRoute.Activity
import kosh.ui.navigation.routes.HomeRoute.Assets
import kosh.ui.navigation.routes.HomeRoute.WalletConnect
import kosh.ui.navigation.routes.RootRoute
import org.jetbrains.compose.resources.stringResource

enum class HomeTab(
    val label: @Composable () -> String,
    val icon: @Composable () -> ImageVector,
) {
    Assets(
        { stringResource(Res.string.assets_title) },
        { kosh.ui.resources.icons.Assets }
    ),
    Activity(
        { stringResource(Res.string.activity_title) },
        { kosh.ui.resources.icons.Activity }
    ),
    WC(
        { stringResource(Res.string.wc_title) },
        { kosh.ui.resources.icons.WcIcon }
    ),
}

@Composable
fun HomeHost(
    link: RootRoute?,
    onResult: @DisallowComposableCalls (RouteResult.Result) -> Unit,
) {
    val pagesRouter = rememberPagesRouter(
        { Pages(listOf(Assets(), Activity(), WalletConnect()), 0).update(link) },
    ) {
        onResult(RouteResult.Result())
    }

    selector(link) { newLink ->
        pagesRouter.navigate { pages -> pages.update(newLink) }
    }

    val childPages by pagesRouter.pages.subscribeAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                selected = childPages.selectedIndex,
                items = HomeTab.entries,
                onSelect = { index -> pagesRouter.select(index) },
            )
        },
    ) { contentPadding ->
        PagesHost(
            pagesRouter = pagesRouter,
            modifier = Modifier
                .padding(bottom = contentPadding.calculateBottomPadding())
                .consumeWindowInsets(WindowInsets(bottom = contentPadding.calculateBottomPadding()))
        ) { route ->
            when (route) {
                is Assets -> RootHost(
                    start = RootRoute.Tokens(),
                    link = route.link
                ) { onResult(it) }

                is Activity -> RootHost(
                    start = RootRoute.Transactions(),
                    link = route.link
                ) { onResult(it) }

                is WalletConnect -> RootHost(
                    start = RootRoute.WalletConnect(),
                    link = route.link
                ) { onResult(it) }
            }
        }
    }
}

private fun Pages<HomeRoute>.update(
    link: RootRoute?,
): Pages<HomeRoute> {
    val affinity = when (link) {
        is RootRoute.Transactions -> Activity::class
        is RootRoute.WalletConnect -> WalletConnect::class
        is RootRoute.Tokens -> Assets::class
        else -> null
    }

    val index = if (affinity != null) {
        items.indexOfFirst { it::class == affinity }
            .takeIf { it >= 0 }
            ?: selectedIndex
    } else {
        selectedIndex
    }

    val homeRoute = when (items[index]) {
        is Activity -> Activity(link)
        is Assets -> Assets(link)
        is WalletConnect -> WalletConnect(link)
    }

    return copy(
        items = items.toMutableList().apply {
            set(index, homeRoute)
        }.toList(),
        selectedIndex = index,
    )
}

@Composable
internal fun NavigationBar(
    selected: Int,
    items: List<HomeTab>,
    onSelect: (Int) -> Unit,
) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon(), item.label()) },
                label = { Text(item.label()) },
                selected = index == selected,
                onClick = { onSelect(index) },
            )
        }
    }
}
