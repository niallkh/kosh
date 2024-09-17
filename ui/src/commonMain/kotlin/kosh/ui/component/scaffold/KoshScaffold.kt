package kosh.ui.component.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import kosh.ui.component.topbar.KoshLargeTopBar

@Composable
fun KoshScaffold(
    modifier: Modifier = Modifier,
    onUp: (() -> Unit)?,
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: (@Composable () -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = floatingActionButton ?: {},
        topBar = {
            KoshLargeTopBar(
                onUp = onUp,
                scrollBehavior = scrollBehavior,
                actions = actions
            ) {
                title()
            }
        },
        bottomBar = bottomBar,
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.imePadding(),
                hostState = snackbarHostState
            )
        },
    ) { paddingValues ->
        CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
            content(paddingValues)
        }
    }
}
