package kosh.ui.network

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kosh.domain.entities.NetworkEntity
import kosh.presentation.component.textfield.TextFieldState
import kosh.presentation.network.Network
import kosh.presentation.network.NetworkFormState
import kosh.presentation.network.rememberNetwork
import kosh.presentation.network.rememberNetworkForm
import kosh.ui.component.button.LoadingButton
import kosh.ui.component.button.PrimaryButtons
import kosh.ui.component.icon.ChainIcon
import kosh.ui.component.menu.AdaptiveMenuItem
import kosh.ui.component.menu.AdaptiveMoreMenu
import kosh.ui.component.nullUnless
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.text.KeyValueRow
import kosh.ui.component.text.TextChainId
import kosh.ui.component.text.TextLine
import kosh.ui.component.textfield.KoshTextField
import kosh.ui.component.textfield.PasteClearIcon
import kosh.ui.failure.AppFailureMessage
import kosh.ui.resources.Res
import kosh.ui.resources.network_input_chain_id_label
import kosh.ui.resources.network_input_explorer_label
import kosh.ui.resources.network_input_icon_label
import kosh.ui.resources.network_input_name_label
import kosh.ui.resources.network_input_read_rpc_label
import kosh.ui.resources.network_input_token_icon_label
import kosh.ui.resources.network_input_token_name_label
import kosh.ui.resources.network_input_token_symbol_label
import kosh.ui.resources.network_input_write_rpc_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun NetworkScreen(
    id: NetworkEntity.Id?,
    onCancel: () -> Unit,
    onFinish: () -> Unit,
    onNavigateUp: () -> Unit,
    onDelete: (NetworkEntity.Id) -> Unit,
) {
    val network = id?.let { rememberNetwork(it) }
    val networkForm = rememberNetworkForm(id)

    KoshScaffold(
        title = { TextLine(network?.entity?.name ?: "New Network") },
        onNavigateUp = { onNavigateUp() },
        actions = {
            network?.entity?.let { network ->
                ChainIcon(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .size(40.dp),
                    icon = network.icon,
                    symbol = network.name,
                    chainId = network.chainId,
                )

                Spacer(Modifier.width(8.dp))

                AdaptiveMoreMenu { dismiss ->
                    AdaptiveMenuItem(
                        text = { Text("Delete") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        },
                        onClick = { dismiss { onDelete(network.id) } }
                    )
                }
            }
        },
    ) { paddingValues ->

        AppFailureMessage(networkForm.failure) {
            networkForm.retry()
        }

        LaunchedEffect(networkForm.saved) {
            if (networkForm.saved) {
                onFinish()
            }
        }

        NetworkContent(
            network = network,
            networkForm = networkForm,
            onCancel = onCancel,
            contentPadding = paddingValues,
        )
    }
}

@Composable
fun NetworkContent(
    network: Network?,
    networkForm: NetworkFormState,
    onCancel: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        network?.entity?.let {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                KeyValueRow(
                    modifier = Modifier.fillMaxWidth(),
                    key = { TextLine("Chain Id") },
                    value = { TextChainId(it.chainId) }
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            NetworkTextFiled(
                modifier = Modifier.fillMaxWidth(),
                textField = networkForm.networkNameTextField,
                label = { Text(stringResource(Res.string.network_input_name_label)) }
            )

            NetworkTextFiled(
                modifier = Modifier.fillMaxWidth(),
                textField = networkForm.readRpcTextField,
                keyboardType = KeyboardType.Uri,
                label = { Text(stringResource(Res.string.network_input_read_rpc_label)) }
            )

            NetworkTextFiled(
                modifier = Modifier.fillMaxWidth(),
                textField = networkForm.writeRpcTextField,
                keyboardType = KeyboardType.Uri,
                label = { Text(stringResource(Res.string.network_input_write_rpc_label)) },
            )

            if (network == null) {
                NetworkTextFiled(
                    modifier = Modifier.fillMaxWidth(),
                    textField = networkForm.chainIdTextField,
                    keyboardType = KeyboardType.Number,
                    label = { Text(stringResource(Res.string.network_input_chain_id_label)) }
                )
            }

            NetworkTextFiled(
                modifier = Modifier.fillMaxWidth(),
                textField = networkForm.tokenNameTextField,
                label = { Text(stringResource(Res.string.network_input_token_name_label)) },
            )

            NetworkTextFiled(
                modifier = Modifier.fillMaxWidth(),
                textField = networkForm.tokenSymbolTextField,
                label = { Text(stringResource(Res.string.network_input_token_symbol_label)) }
            )

            NetworkTextFiled(
                modifier = Modifier.fillMaxWidth(),
                textField = networkForm.explorerTextField,
                keyboardType = KeyboardType.Uri,
                label = { Text(stringResource(Res.string.network_input_explorer_label)) }
            )

            NetworkTextFiled(
                modifier = Modifier.fillMaxWidth(),
                textField = networkForm.networkIconTextField,
                keyboardType = KeyboardType.Uri,
                label = { Text(stringResource(Res.string.network_input_icon_label)) }
            )

            NetworkTextFiled(
                modifier = Modifier.fillMaxWidth(),
                textField = networkForm.tokenIconTextField,
                keyboardType = KeyboardType.Uri,
                label = { Text(stringResource(Res.string.network_input_token_icon_label)) },
                onDone = {
                    defaultKeyboardAction(ImeAction.Done)
                    networkForm.save()
                }
            )
        }

        PrimaryButtons(
            modifier = Modifier.fillMaxWidth(),
            cancel = {
                TextButton(onClick = onCancel) {
                    Text("Cancel")
                }
            },
            confirm = {
                LoadingButton(networkForm.loading, onClick = { networkForm.save() }) {
                    Text("Save")
                }
            }
        )
    }
}

@Composable
fun NetworkTextFiled(
    modifier: Modifier = Modifier,
    textField: TextFieldState<*, *>,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    label: @Composable () -> Unit,
    onDone: (KeyboardActionScope.() -> Unit)? = null,
) {
    var focused by remember { mutableStateOf(false) }

    KoshTextField(
        modifier = modifier.onFocusChanged { focused = it.isFocused },
        value = textField.value,
        onValueChange = textField.onChange,
        error = textField.error?.firstOrNull()?.message,
        readOnly = readOnly,
        keyboardActions = remember(onDone) {
            KeyboardActions(onDone = onDone)
        },
        keyboardOptions = remember(onDone, keyboardType) {
            KeyboardOptions(
                imeAction = ImeAction.Done.takeIf { onDone != null } ?: ImeAction.Next,
                keyboardType = keyboardType,
            )
        },
        label = label,
        trailingIcon = nullUnless(focused) {
            PasteClearIcon(textField.value, textField.onChange)
        }
    )
}
