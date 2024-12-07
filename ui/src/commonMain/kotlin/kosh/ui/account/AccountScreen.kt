package kosh.ui.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import arrow.core.raise.nullable
import kosh.domain.entities.AccountEntity
import kosh.domain.usecases.account.accountNameValidator
import kosh.presentation.account.rememberAccount
import kosh.presentation.account.rememberUpdateAccount
import kosh.presentation.component.textfield.TextFieldValidatedState
import kosh.presentation.component.textfield.rememberTextFieldValidated
import kosh.presentation.network.rememberNetworks
import kosh.presentation.network.rememberOpenExplorer
import kosh.ui.component.button.LoadingButton
import kosh.ui.component.button.PrimaryButtons
import kosh.ui.component.icon.AccountIcon
import kosh.ui.component.menu.AdaptiveMenuItem
import kosh.ui.component.menu.AdaptiveMoreMenu
import kosh.ui.component.nullUnless
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.text.KeyValueRow
import kosh.ui.component.text.TextAddressShort
import kosh.ui.component.text.TextDerivationPath
import kosh.ui.component.text.TextLine
import kosh.ui.component.textfield.KoshTextField
import kosh.ui.component.textfield.PasteClearIcon
import kosh.ui.failure.AppFailureMessage

@Composable
fun AccountScreen(
    id: AccountEntity.Id,
    onCancel: () -> Unit,
    onDelete: (AccountEntity.Id) -> Unit,
    onNavigateUp: () -> Unit,
) {
    val account = rememberAccount(id)
    val updateAccount = rememberUpdateAccount(id)

    KoshScaffold(
        title = {
            TextLine(account.entity?.name)
        },
        onNavigateUp = { onNavigateUp() },
        actions = {
            AccountIcon(account.entity?.address)

            AdaptiveMoreMenu { dismiss ->
                val networkId = rememberNetworks().enabled.firstOrNull()
                val openExplorer = networkId?.let { rememberOpenExplorer(it) }

                AdaptiveMenuItem(
                    leadingIcon = {
                        Icon(Icons.AutoMirrored.Filled.OpenInNew, "Open in explorer")
                    },
                    onClick = {
                        dismiss {
                            nullable {
                                ensureNotNull(openExplorer)
                                    .openAddress(ensureNotNull(account.entity).address)
                            }
                        }
                    },
                ) {
                    Text("Open In Explorer")
                }

                AdaptiveMenuItem(
                    text = { Text("Delete") },
                    leadingIcon = { Icon(Icons.Default.Delete, contentDescription = "Delete") },
                    onClick = {
                        dismiss {
                            nullable {
                                onDelete(ensureNotNull(account.entity).id)
                            }
                        }
                    }
                )
            }
        }
    ) { paddingValues ->

        AppFailureMessage(updateAccount.failure)

        LaunchedEffect(updateAccount.updated) {
            if (updateAccount.updated) {
                onCancel()
            }
        }

        AccountContent(
            account = account.entity,
            onCancel = { onCancel() },
            onUpdate = { updateAccount.update(it) },
            contentPadding = paddingValues,
        )
    }
}

@Composable
fun AccountContent(
    account: AccountEntity?,
    onUpdate: (String) -> Unit,
    onCancel: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .padding(contentPadding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            KeyValueRow(
                modifier = Modifier.fillMaxWidth(),
                key = { TextLine("Address") },
                value = { TextAddressShort(account?.address, clickable = true) }
            )

            KeyValueRow(
                modifier = Modifier.fillMaxWidth(),
                key = { TextLine("Derivation path") },
                value = { TextDerivationPath(account?.derivationPath, clickable = true) }
            )
        }

        val textField = rememberTextFieldValidated(account?.name) {
            accountNameValidator(it)
        }

        AccountNameTextField(
            textField = textField,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            textField.validate().getOrNull()?.let {
                onUpdate(it)
            }
        }

        PrimaryButtons(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            cancel = {
                TextButton(onCancel) {
                    Text("Cancel")
                }
            },
            confirm = {
                LoadingButton(false, {
                    textField.validate().getOrNull()?.let {
                        onUpdate(it)
                    }
                }) {
                    Text("Save")
                }
            }
        )
    }
}

@Composable
private fun AccountNameTextField(
    textField: TextFieldValidatedState<*, *>,
    modifier: Modifier = Modifier,
    onDone: () -> Unit,
) {
    var focused by remember { mutableStateOf(false) }

    KoshTextField(
        modifier = modifier.onFocusChanged { focused = it.isFocused },
        value = textField.value,
        onValueChange = textField.onChange,
        error = textField.error?.firstOrNull()?.message,
        keyboardActions = remember(onDone) {
            KeyboardActions(onDone = { onDone() })
        },
        label = { Text("Account Name") },
        trailingIcon = nullUnless(focused) {
            PasteClearIcon(textField.value, textField.onChange)
        }
    )
}
