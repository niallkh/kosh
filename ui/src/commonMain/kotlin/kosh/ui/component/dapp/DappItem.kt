package kosh.ui.component.dapp

import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.models.reown.DappMetadata
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
            dapp?.url?.let {
                TextUri(uri = it)
            }
        },
        leadingContent = { DappIcon(dapp) },
    )
}
