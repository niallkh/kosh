package kosh.ui.wc

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.models.wc.WcSessionProposal
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.single.single
import kosh.ui.component.text.TextLine
import kosh.ui.component.text.TextUri

@Composable
fun WcProposalItem(
    proposal: WcSessionProposal,
    onSelect: (WcSessionProposal) -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        modifier = modifier.clickable(onClick = { onSelect(proposal) }.single()),
        leadingContent = {
            DappIcon(
                proposal.dapp.url,
                proposal.dapp.icon,
            )
        },
        headlineContent = {
            TextLine(proposal.dapp.name)
        },
        supportingContent = {
            proposal.dapp.url?.let {
                TextUri(it)
            }
        },
        trailingContent = {
            Icon(Icons.Outlined.Handshake, "Proposal")
        }
    )
}
