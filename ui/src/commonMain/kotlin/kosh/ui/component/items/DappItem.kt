package kosh.ui.component.items

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.models.reown.DappMetadata
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.modifier.optionalClickable
import kosh.ui.component.text.TextLine
import kosh.ui.component.text.TextUri

@Composable
fun DappItem(
    dapp: DappMetadata?,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    ListItem(
        modifier = modifier.optionalClickable(dapp != null, onClick),
        headlineContent = { TextLine(text = dapp?.name) },
        supportingContent = {
            if (dapp == null || dapp.url != null) {
                TextUri(uri = dapp?.url)
            }
        },
        leadingContent = { DappIcon(dapp, Modifier.size(40.dp)) },
    )
}
