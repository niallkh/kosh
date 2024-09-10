package kosh.ui.navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import kosh.ui.resources.Res
import kosh.ui.resources.icons.WcIcon
import kosh.ui.resources.portfolio_title
import kosh.ui.resources.transactions_title
import kosh.ui.resources.wc_sessions_title
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource

@Serializable
sealed interface HomeRoute : Route {
    val title: String
        @Composable get
    val label: String
        @Composable get
    val icon: ImageVector
        @Composable get
    val fab: ImageVector?
        @Composable get

    @Serializable
    data object Assets : HomeRoute {
        override val title: String
            @Composable get() = stringResource(Res.string.portfolio_title)
        override val label: String
            @Composable get() = title
        override val icon: ImageVector
            @Composable get() = kosh.ui.resources.icons.Assets
        override val fab: ImageVector
            @Composable get() = Icons.Default.Add
    }

    @Serializable
    data object Activity : HomeRoute {
        override val title: String
            @Composable get() = stringResource(Res.string.transactions_title)
        override val label: String
            @Composable get() = title
        override val icon: ImageVector
            @Composable get() = kosh.ui.resources.icons.Activity
        override val fab: ImageVector?
            @Composable get() = null
    }

    @Serializable
    data object WalletConnect : HomeRoute {
        override val title: String
            @Composable get() = stringResource(Res.string.wc_sessions_title)
        override val label: String
            @Composable get() = title
        override val icon: ImageVector
            @Composable get() = WcIcon
        override val fab: ImageVector
            @Composable get() = Icons.Default.Add
    }
}
