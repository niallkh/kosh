package kosh.ui.navigation.hosts.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import kosh.ui.generated.resources.Res
import kosh.ui.generated.resources.activity_title
import kosh.ui.generated.resources.assets_title
import kosh.ui.generated.resources.wc_title
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
