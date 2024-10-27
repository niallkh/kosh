package kosh.ui.component.items

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        leadingContent = { AccountIcon(address, Modifier.size(40.dp)) },
        headlineContent = { TextAddressShort(address) },
    )
}
