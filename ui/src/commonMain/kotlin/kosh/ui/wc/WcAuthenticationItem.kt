package kosh.ui.wc

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.models.wc.WcAuthentication
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.single.single
import kosh.ui.component.text.TextLine
import kosh.ui.component.text.TextUri
import kosh.ui.resources.icons.PersonalSignature

@Composable
fun WcAuthenticationItem(
    authentication: WcAuthentication,
    onSelect: (WcAuthentication) -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        modifier = modifier.clickable(onClick = { onSelect(authentication) }.single()),
        leadingContent = {
            DappIcon(authentication.dapp)
        },
        headlineContent = {
            TextLine(authentication.dapp.name)
        },
        supportingContent = {
            authentication.dapp.url?.let {
                TextUri(it)
            }
        },
        trailingContent = {
            Icon(PersonalSignature, "Proposal")
        }
    )
}
