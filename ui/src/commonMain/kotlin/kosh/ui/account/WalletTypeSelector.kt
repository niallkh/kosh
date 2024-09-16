package kosh.ui.account

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.entities.WalletEntity
import kosh.ui.component.bottomsheet.KoshModalBottomSheet
import kosh.ui.resources.icons.LedgerIcon
import kosh.ui.resources.icons.TrezorIcon

@Composable
fun WalletTypeSelector(
    visible: Boolean,
    onSelected: (WalletEntity.Type) -> Unit,
    onDismiss: () -> Unit,
) {
    if (visible) {
        KoshModalBottomSheet(
            onDismissRequest = onDismiss,
            containerColor = MaterialTheme.colorScheme.background,
        ) { dismiss ->
            ListItemDefaults.containerColor
            ListItem(
                modifier = Modifier.clickable { dismiss { onSelected(WalletEntity.Type.Trezor) } },
                leadingContent = { Icon(TrezorIcon, "Trezor") },
                headlineContent = { Text("Trezor Wallet") },
            )

            ListItem(
                modifier = Modifier.clickable { dismiss { onSelected(WalletEntity.Type.Ledger) } },
                leadingContent = { Icon(LedgerIcon, "Ledger") },
                headlineContent = { Text("Ledger Wallet") }
            )
        }
    }
}
