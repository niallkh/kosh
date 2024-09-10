package kosh.ui.trezor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import kosh.ui.component.textfield.KoshTextField
import kosh.ui.resources.Res
import kosh.ui.resources.trezor_passphrase_dialog_confirm
import kosh.ui.resources.trezor_passphrase_dialog_enter_on_device
import kosh.ui.resources.trezor_passphrase_dialog_save
import kosh.ui.resources.trezor_passphrase_textfield_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun TrezorPassphraseDialog(
    visible: Boolean = true,
    canSave: Boolean = true,
    onDismiss: () -> Unit = {},
    onDeviceEnter: () -> Unit = {},
    onConfirm: (String, Boolean) -> Unit,
) {
    if (visible) {
        var passphrase by remember { mutableStateOf(TextFieldValue("")) }
        var savePassphrase by remember { mutableStateOf(false) }

        AlertDialog(
            properties = DialogProperties(/*securePolicy = SecureFlagPolicy.SecureOn*/),
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = { onConfirm(passphrase.text, canSave && savePassphrase) }) {
                    Text(stringResource(Res.string.trezor_passphrase_dialog_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = onDeviceEnter) {
                    Text(stringResource(Res.string.trezor_passphrase_dialog_enter_on_device))
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PassphraseTextField(
                        value = passphrase,
                        onChange = { passphrase = it },
                        onConfirm = { onConfirm(passphrase.text, canSave && savePassphrase) },
                    )

                    if (canSave) {
                        ListItemDefaults.colors()
                        ListItem(
                            headlineContent = { Text(stringResource(Res.string.trezor_passphrase_dialog_save)) },
                            trailingContent = {
                                Checkbox(
                                    checked = savePassphrase,
                                    onCheckedChange = { savePassphrase = it }
                                )
                            }
                        )
                    }
                }
            },
        )
    }
}

@Composable
fun PassphraseTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onChange: (TextFieldValue) -> Unit,
    onConfirm: () -> Unit,
) {
    KoshTextField(
        modifier = modifier,
        value = value,
        onValueChange = onChange,
        label = { Text(stringResource(Res.string.trezor_passphrase_textfield_label)) },
        keyboardActions = remember(onConfirm) { KeyboardActions { onConfirm() } },
        keyboardOptions = remember {
            KeyboardOptions(
                autoCorrect = false,
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Password,
            )
        },
        requestFocus = true,
    )
}
