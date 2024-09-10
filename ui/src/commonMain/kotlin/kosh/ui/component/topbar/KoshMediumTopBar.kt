package kosh.ui.component.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable

@Composable
fun KoshMediumTopBar(
    onUp: (() -> Unit)?,
    scrollBehavior: TopAppBarScrollBehavior?,
    actions: @Composable RowScope.() -> Unit = {},
    title: @Composable () -> Unit,
) {
    MediumTopAppBar(
        scrollBehavior = scrollBehavior,
        title = title,
        navigationIcon = { NavigationIcon(onUp) },
        actions = actions,
    )
}
