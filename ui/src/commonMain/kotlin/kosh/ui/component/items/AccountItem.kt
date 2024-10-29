package kosh.ui.component.items

import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.entities.AccountEntity
import kosh.ui.component.icon.AccountIcon
import kosh.ui.component.modifier.optionalClickable
import kosh.ui.component.single.single
import kosh.ui.component.text.TextAddressShort
import kosh.ui.component.text.TextLine

@Composable
fun AccountItem(
    account: AccountEntity?,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    ListItem(
        modifier = modifier.optionalClickable(account != null, onClick?.single()),
        leadingContent = { AccountIcon(account?.address) },
        headlineContent = { TextLine(account?.name) },
        supportingContent = { TextAddressShort(account?.address) },
        trailingContent = trailingContent
    )
}
