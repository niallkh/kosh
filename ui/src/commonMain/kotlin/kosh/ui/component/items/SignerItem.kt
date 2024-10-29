package kosh.ui.component.items

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.entities.AccountEntity
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
    account: AccountEntity? = rememberAccount(signer.address).entity,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = modifier.clickable(onClick = onClick.single()),
        leadingContent = { AccountIcon(signer.address) },
        headlineContent = {
            account?.let { TextLine(it.name) }
                ?: TextAddressShort(signer.address)
        },
        supportingContent = { TextDerivationPath(signer.derivationPath) }
    )
}
