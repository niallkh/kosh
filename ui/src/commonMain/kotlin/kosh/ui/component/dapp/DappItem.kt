package kosh.ui.component.dapp

import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import kosh.domain.models.reown.DappMetadata
import kosh.ui.component.modifier.optionalClickable
import kosh.ui.component.text.TextUri


@Composable
fun DappItem(
    dapp: DappMetadata,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    ListItem(
        modifier = modifier.optionalClickable(onClick),
        headlineContent = {
            Text(
                text = dapp.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        supportingContent = {
            dapp.url?.let {
                TextUri(uri = it)
            }
        },
        leadingContent = {
            DappIcon(dapp.url, dapp.icon)
        },
    )
}
