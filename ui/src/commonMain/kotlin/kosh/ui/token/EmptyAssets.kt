package kosh.ui.token

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kosh.ui.component.illustration.Illustration
import kosh.ui.resources.illustrations.AssetsEmpty


@Composable
internal fun EmptyAssetsContent(
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    Illustration(
        AssetsEmpty(),
        "AssetsEmpty",
        modifier
            .fillMaxWidth()
            .padding(64.dp),
    ) {
        content()
    }
}

@Composable
internal fun OpenNetworks(
    onOpenNetworks: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "Get started by adding your first network",
            textAlign = TextAlign.Center
        )

        TextButton(onClick = {
            onOpenNetworks()
        }) {
            Text("Add Network")
        }
    }
}

@Composable
internal fun OpenWallets(
    onOpenWallets: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "Get started by adding your first wallet",
            textAlign = TextAlign.Center
        )

        TextButton(onClick = {
            onOpenWallets()
        }) {
            Text("Add Wallet")
        }
    }
}
