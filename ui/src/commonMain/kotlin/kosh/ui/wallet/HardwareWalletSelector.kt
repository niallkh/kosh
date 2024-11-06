@file:OptIn(ExperimentalAnimationApi::class)

package kosh.ui.wallet

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.models.hw.HardwareWallet
import kosh.presentation.wallet.rememberHardwareWallets
import kosh.ui.component.bottomsheet.KoshModalBottomSheet
import kosh.ui.component.items.HardwareWalletItem

@Composable
fun HardwareWalletSelector(
    onDismiss: () -> Unit,
    onSelected: (HardwareWallet) -> Unit,
) {
    KoshModalBottomSheet(
        onDismissRequest = onDismiss,
    ) { dismiss ->
        val deviceList = rememberHardwareWallets()

        val transition = updateTransition(deviceList.wallets)

        transition.Crossfade(
            contentKey = { it.isEmpty() }
        ) {
            if (it.isEmpty()) {
                Box(Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center).padding(8.dp))
                }
            } else {
                Column {
                    for (hw in it) {
                        key(hw.id.value) {
                            HardwareWalletItem(
                                hardwareWallet = hw,
                                onSelected = { dismiss { onSelected(it) } }
                            )
                        }
                    }
                }
            }
        }
    }
}
