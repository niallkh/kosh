package kosh.ui.component.signer

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.models.web3.Signer
import kosh.presentation.account.rememberAccount
import kosh.ui.component.icon.AccountIcon
import kosh.ui.component.single.single
import kosh.ui.component.text.TextAddressShort
import kosh.ui.component.text.TextDerivationPath
import kosh.ui.component.text.TextLine

@Composable
fun SignerItem(
    signer: Signer,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val account = rememberAccount(signer.address)

    ListItem(
        modifier = modifier.clickable(onClick = onClick.single()),
        leadingContent = {
            AccountIcon(
                modifier = Modifier,
                address = signer.address,
            )
        },
        headlineContent = {
            account.entity?.let { TextLine(it.name) }
                ?: TextAddressShort(signer.address)
        },
        trailingContent = {
            TextDerivationPath(signer.derivationPath)
        }
    )
}
