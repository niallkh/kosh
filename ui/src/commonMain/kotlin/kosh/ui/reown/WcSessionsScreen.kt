package kosh.ui.reown

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kosh.domain.models.reown.WcSession
import kosh.domain.serializers.ImmutableList
import kosh.presentation.wc.rememberSessions
import kosh.ui.component.illustration.Illustration
import kosh.ui.component.items.DappItem
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.single.single
import kosh.ui.resources.icons.Networks
import kosh.ui.resources.illustrations.DappsEmpty
import kotlinx.collections.immutable.toPersistentList

@Composable
fun WcSessionsScreen(
    onOpen: (WcSession) -> Unit,
    onOpenNetworks: () -> Unit,
    onOpenWallets: () -> Unit,
    onPair: () -> Unit,
) {
    val sessions = rememberSessions()

    KoshScaffold(
        title = { Text("WalletConnect") },
        onNavigateUp = null,
        actions = {
            IconButton(onClick = onOpenNetworks.single()) {
                Icon(Networks, "Networks")
            }

            IconButton(onClick = onOpenWallets.single()) {
                Icon(Icons.Outlined.AccountBalanceWallet, "Accounts")
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onPair.single()) {
                Icon(Icons.Default.Add, contentDescription = "Add Dapp")
            }
        }
    ) { contentPadding ->
        WcSessionsContent(
            paddingValues = contentPadding,
            sessions = sessions.sessions,
            init = sessions.init,
            onSelect = onOpen,
        )
    }
}

@Composable
fun WcSessionsContent(
    paddingValues: PaddingValues,
    sessions: ImmutableList<WcSession>,
    init: Boolean,
    onSelect: (WcSession) -> Unit,
) {
    LazyColumn(
        contentPadding = paddingValues,
        modifier = Modifier.fillMaxSize()
    ) {

        when {
            !init -> sessions(
                sessions = List(7) { null }.toPersistentList(),
                onSelect = onSelect
            )

            sessions.isEmpty() -> {
                item {
                    EmptyDappsContent(Modifier.animateItem())
                }
            }

            else -> sessions(
                sessions = sessions,
                onSelect = onSelect
            )
        }

        item {
            Spacer(Modifier.height(64.dp))
        }
    }
}

private fun LazyListScope.sessions(
    sessions: ImmutableList<WcSession?>,
    onSelect: (WcSession) -> Unit,
) {
    items(
        count = sessions.size,
        key = { sessions[it]?.id?.sessionTopic?.value ?: it }
    ) { index ->
        val session = sessions[index]

        DappItem(
            modifier = Modifier.animateItem(),
            dapp = session?.dapp,
            onClick = { session?.let { onSelect(it) } },
        )
    }
}

@Composable
private fun EmptyDappsContent(
    modifier: Modifier = Modifier,
) {
    Illustration(
        DappsEmpty(),
        "DappsEmpty",
        modifier
            .fillMaxWidth()
            .padding(64.dp),
    ) {
        Text(
            "Get started by connecting your first DApp",
            textAlign = TextAlign.Center
        )
    }
}
