package kosh.ui.component.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kosh.ui.component.topbar.KoshLargeTopBar
import kosh.ui.component.topbar.KoshMediumTopBar

@Composable
fun KoshScaffold(
    modifier: Modifier = Modifier,
    onUp: (() -> Unit)?,
    largeTopBar: Boolean = true,
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier.then(
            scrollBehavior.nestedScrollConnection
                .let { Modifier.nestedScroll(it) }
        ),
        floatingActionButton = floatingActionButton ?: {},
        topBar = {
            if (largeTopBar) {
                KoshLargeTopBar(
                    onUp = onUp,
                    scrollBehavior = scrollBehavior,
                    actions = actions
                ) {
                    title()
                }
            } else {
                KoshMediumTopBar(
                    onUp = onUp,
                    scrollBehavior = scrollBehavior,
                    actions = actions,
                ) {
                    title()
                }
            }
        },
        bottomBar = bottomBar
    ) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
                .fillMaxSize()
        ) {
            content()
        }
    }

    if (floatingActionButton != null) {
        ProvideSnackbarOffset(80.dp)
    }
}
