package kosh.ui.transaction.calls

import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.models.Address
import kosh.domain.models.orZero
import kosh.ui.component.icon.AccountIcon
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.text.TextAddressShort

@Composable
fun AccountItem(
    address: Address?,
    modifier: Modifier = Modifier,
    overlineContent: @Composable () -> Unit,
) {
    ListItem(
        modifier = modifier,
        overlineContent = overlineContent,
        leadingContent = {
            AccountIcon(
                address.orZero(),
                Modifier.placeholder(address == null),
            )
        },
        headlineContent = {
            TextAddressShort(
                address.orZero(),
                Modifier.placeholder(address == null),
            )
        },
    )
}
