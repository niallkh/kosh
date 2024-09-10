package kosh.ui.component.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable

@Composable
fun KoshLargeTopBar(
    onUp: (() -> Unit)?,
    scrollBehavior: TopAppBarScrollBehavior?,
    actions: @Composable RowScope.() -> Unit = {},
    title: @Composable () -> Unit,
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        title = title,
        navigationIcon = { NavigationIcon(onUp) },
        actions = actions,
    )
}
