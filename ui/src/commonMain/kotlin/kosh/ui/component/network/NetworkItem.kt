package kosh.ui.component.network

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.entities.NetworkEntity
import kosh.ui.component.icon.ChainIcon
import kosh.ui.component.modifier.optionalClickable
import kosh.ui.component.single.single
import kosh.ui.component.text.TextLine


@Composable
fun NetworkItem(
    network: NetworkEntity?,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    val color = if (selected) MaterialTheme.colorScheme.secondaryContainer
    else MaterialTheme.colorScheme.surface

    ListItem(
        modifier = modifier.optionalClickable(network != null, onClick?.single()),
        colors = ListItemDefaults.colors(
            containerColor = color,
        ),
        headlineContent = {
            TextLine(network?.name)
        },
        leadingContent = {
            ChainIcon(
                network = network,
                modifier = Modifier.size(40.dp),
            )
        },
        trailingContent = trailingContent
    )
}
