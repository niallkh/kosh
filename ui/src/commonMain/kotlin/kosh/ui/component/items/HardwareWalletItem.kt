package kosh.ui.component.items

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Usb
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.models.hw.HardwareWallet
import kosh.domain.models.hw.HardwareWallet.Transport
import kosh.domain.models.hw.Ledger
import kosh.domain.models.hw.Trezor
import kosh.ui.component.text.TextLine
import kosh.ui.resources.icons.LedgerIcon
import kosh.ui.resources.icons.TrezorIcon

@Composable
fun HardwareWalletItem(
    hardwareWallet: HardwareWallet,
    onSelected: (HardwareWallet) -> Unit,
) {
    when (hardwareWallet) {
        is Trezor -> ListItem(
            modifier = Modifier.clickable { onSelected(hardwareWallet) },
            leadingContent = { Icon(TrezorIcon, "Trezor") },
            headlineContent = { TextLine(hardwareWallet.product ?: "Trezor") },
            trailingContent = { TransportIcon(hardwareWallet.transport) },
        )

        is Ledger -> ListItem(
            modifier = Modifier.clickable { onSelected(hardwareWallet) },
            leadingContent = { Icon(LedgerIcon, "Ledger") },
            headlineContent = { TextLine(hardwareWallet.product ?: "Ledger") },
            trailingContent = { TransportIcon(hardwareWallet.transport) },
        )
    }
}

@Composable
private fun TransportIcon(transport: Transport) {
    when (transport) {
        Transport.USB -> Icon(
            Icons.Default.Usb,
            contentDescription = "Usb"
        )

        Transport.BLE -> Icon(
            Icons.Default.Bluetooth,
            contentDescription = "Bluetooth"
        )
    }
}
