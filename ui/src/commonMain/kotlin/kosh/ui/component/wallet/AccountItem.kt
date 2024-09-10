package kosh.ui.component.wallet

import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.entities.AccountEntity
import kosh.domain.models.orZero
import kosh.ui.component.icon.AccountIcon
import kosh.ui.component.modifier.optionalClickable
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.single.single
import kosh.ui.component.text.TextAddressShort
import kosh.ui.component.text.TextLine

@Composable
fun AccountItem(
    account: AccountEntity?,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    ListItem(
        modifier = modifier.optionalClickable(onClick?.single()),
        leadingContent = {
            AccountIcon(
                address = account?.address.orZero(),
                Modifier.placeholder(account == null)
            )
        },
        headlineContent = {
            TextLine(
                account?.name ?: "Unknown Account",
                Modifier.placeholder(account == null)
            )
        },
        supportingContent = {
            TextAddressShort(
                account?.address.orZero(),
                Modifier.placeholder(account == null)
            )
        },
        trailingContent = trailingIcon
    )
}
