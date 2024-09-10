package kosh.ui.component.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import kosh.ui.component.single.single

@Composable
fun NavigationIcon(
    onUp: (() -> Unit)?,
) {
    when {
        onUp != null -> IconButton(onClick = onUp.single()) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Up navigation"
            )
        }

        else -> Unit
    }
}
