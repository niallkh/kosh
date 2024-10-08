package kosh.ui.component.topbar

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import kosh.ui.component.single.single

@Composable
fun NavigationIcon(
    onUp: (() -> Unit)?,
) {
    when {
        onUp != null -> IconButton(onClick = onUp.single()) {
            Icon(
                imageVector = NavigationIcon,
                contentDescription = "Up navigation"
            )
        }

        else -> Unit
    }
}

expect val NavigationIcon: ImageVector
