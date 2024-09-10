package kosh.ui.failure

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.failure.AppFailure

@Composable
fun <T : AppFailure> AppFailureItem(
    failure: T,
    modifier: Modifier = Modifier,
    retry: (() -> Unit)? = null,
) {
    val trailingContent = @Composable {
        if (retry != null) {
            IconButton(retry) {
                Icon(Icons.Default.Refresh, contentDescription = null)
            }
        }
    }

    ListItem(
        modifier = modifier.fillMaxWidth(),
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
        ),
        leadingContent = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        },
        headlineContent = {
            Text(failure.message)
        },
        trailingContent = trailingContent.takeIf { retry != null }
    )
}
