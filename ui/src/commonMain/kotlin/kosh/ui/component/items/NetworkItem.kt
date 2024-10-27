package kosh.ui.component.items

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
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
    onClick: (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {

    ListItem(
        modifier = modifier.optionalClickable(network != null, onClick?.single()),
        headlineContent = { TextLine(network?.name) },
        leadingContent = {
            ChainIcon(
                network = network,
                modifier = Modifier.size(40.dp),
            )
        },
        trailingContent = trailingContent
    )
}
