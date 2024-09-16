package kosh.ui.wc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eygraber.uri.Uri
import kosh.domain.models.orZero
import kosh.domain.models.wc.WcRequest
import kosh.presentation.wc.AddNetworkRequestState
import kosh.presentation.wc.rememberAddNetworkRequest
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.button.LoadingButton
import kosh.ui.component.button.PrimaryButtons
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.text.KeyValueColumn
import kosh.ui.component.text.TextChainId
import kosh.ui.component.text.TextLine
import kosh.ui.component.text.TextUri
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage

@Composable
fun WcAddNetworkScreen(
    id: WcRequest.Id,
    onCancel: () -> Unit,
    onResult: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    val addNetwork = rememberAddNetworkRequest(id)

    AppFailureMessage(addNetwork.networkFailure)

    LaunchedEffect(addNetwork.added) {
        if (addNetwork.added) {
            onResult()
        }
    }

    WcAddNetworkContent(
        addNetwork = addNetwork,
        onNavigateUp = onNavigateUp,
        onReject = {
            addNetwork.reject()
            onCancel()
        },
    )
}

@Composable
fun WcAddNetworkContent(
    addNetwork: AddNetworkRequestState,
    onNavigateUp: () -> Unit,
    onReject: () -> Unit,
) {
    KoshScaffold(
        title = { TextLine("Add Network") },
        onUp = { onNavigateUp() },
    ) { paddingValues ->
        addNetwork.failure?.let {
            AppFailureItem(it) { addNetwork.retry() }
        } ?: Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            KeyValueColumn(
                key = { Text("Chain Id") },
                value = {
                    TextChainId(
                        addNetwork.call?.chainId.orZero(),
                        Modifier.placeholder(addNetwork.call == null)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            KeyValueColumn(
                key = { Text("Network Name") },
                value = {
                    TextLine(
                        addNetwork.call?.chainName ?: "Unknown Network Name",
                        Modifier.placeholder(addNetwork.call == null)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            KeyValueColumn(
                key = { Text("Token Name") },
                value = {
                    TextLine(
                        addNetwork.call?.tokenName ?: "Unknown Token Name",
                        Modifier.placeholder(addNetwork.call == null)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            KeyValueColumn(
                key = { Text("Token Symbol") },
                value = {
                    TextLine(
                        addNetwork.call?.tokenSymbol ?: "UNKWN",
                        Modifier.placeholder(addNetwork.call == null)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            KeyValueColumn(
                key = { Text("Rpc Provider") },
                value = {
                    TextUri(
                        addNetwork.call?.rpcProviders?.first() ?: Uri.EMPTY,
                        Modifier.placeholder(addNetwork.call == null)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            addNetwork.call?.explorers?.firstOrNull()?.let { explorer ->
                KeyValueColumn(
                    key = { Text("Explorer") },
                    value = { TextUri(explorer) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            addNetwork.call?.icons?.firstOrNull()?.let { icon ->
                KeyValueColumn(
                    key = { Text("Icon") },
                    value = { TextUri(icon) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            PrimaryButtons(
                modifier = Modifier.fillMaxWidth(),
                cancel = {
                    TextButton(onClick = onReject) {
                        Text("Reject")
                    }
                },
                confirm = {
                    LoadingButton(addNetwork.adding, onClick = {
                        addNetwork.add()
                    }) {
                        Text("Add")
                    }
                }
            )
        }

        LoadingIndicator(addNetwork.loading)
    }
}
