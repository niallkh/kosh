package kosh.ui.component.items

import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.models.Address
import kosh.ui.component.icon.AccountIcon
import kosh.ui.component.text.TextAddressShort

@Composable
fun AddressItem(
    address: Address?,
    modifier: Modifier = Modifier,
    overlineContent: @Composable () -> Unit,
) {
    ListItem(
        modifier = modifier,
        overlineContent = overlineContent,
        leadingContent = { AccountIcon(address) },
        headlineContent = { TextAddressShort(address) },
    )
}
