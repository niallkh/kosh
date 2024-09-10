package kosh.ui.trezor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import kosh.ui.component.textfield.KoshTextFieldReadOnly
import kosh.ui.resources.Res
import kosh.ui.resources.trezor_pin_matrix_dialog_confirm
import kosh.ui.resources.trezor_pin_matrix_textfield_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun TrezorPinMatrixDialog(
    visible: Boolean = true,
    onDismiss: () -> Unit = {},
    onConfirm: (String) -> Unit = {},
) {
    if (visible) {
        var pin by remember { mutableStateOf("") }

        AlertDialog(
            properties = DialogProperties(/*securePolicy = SecureFlagPolicy.SecureOn*/),
            onDismissRequest = onDismiss,
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    PinTextField(
                        length = pin.length,
                        onBackspace = { pin = pin.dropLast(1) }
                    )
                    PinMatrix(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { pin += it.toString() }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { onConfirm(pin) }) {
                    Text(stringResource(Res.string.trezor_pin_matrix_dialog_confirm))
                }
            },
        )
    }
}


@Composable
fun PinTextField(
    modifier: Modifier = Modifier,
    length: Int = 9,
    onBackspace: () -> Unit = {},
) {
    KoshTextFieldReadOnly(
        modifier = modifier,
        textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace),
        label = { Text(stringResource(Res.string.trezor_pin_matrix_textfield_label)) },
        value = remember(length) { "•".repeat(length) },
        trailingIcon = {
            IconButton(
                onClick = onBackspace,
            ) {
                Icon(Icons.AutoMirrored.Default.Backspace, contentDescription = null)
            }
        },
    )
}


@Composable
private fun PinMatrix(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PinButton { onClick(7) }
            PinButton { onClick(8) }
            PinButton { onClick(9) }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PinButton { onClick(4) }
            PinButton { onClick(5) }
            PinButton { onClick(6) }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PinButton { onClick(1) }
            PinButton { onClick(2) }
            PinButton { onClick(3) }
        }
    }
}

@Composable
private fun PinButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    FilledTonalButton(
        modifier = modifier
            .size(64.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "•")
        }
    }
}
