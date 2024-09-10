package kosh.ui.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.ui.component.single.single

@Composable
fun ChoiceButtons(
    modifier: Modifier = Modifier,
    confirmEnabled: Boolean = true,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    cancel: @Composable () -> Unit,
    confirm: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.weight(1f))

        TextButton(
            onClick = onCancel.single()
        ) {
            cancel()
        }

        Button(
            onClick = onConfirm.single(),
            enabled = confirmEnabled
        ) {
            confirm()
        }
    }
}
