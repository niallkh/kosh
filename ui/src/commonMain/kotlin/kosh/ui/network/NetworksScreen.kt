package kosh.ui.network

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kosh.domain.entities.NetworkEntity
import kosh.domain.models.ethereum
import kosh.domain.serializers.ImmutableList
import kosh.domain.serializers.ImmutableSet
import kosh.domain.uuid.leastSignificantBits
import kosh.presentation.network.rememberNetworks
import kosh.presentation.network.rememberToggleNetwork
import kosh.ui.component.illustration.Illustration
import kosh.ui.component.items.NetworkItem
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.single.single
import kosh.ui.resources.Res
import kosh.ui.resources.illustrations.NetworksEmpty
import kosh.ui.resources.networks_title
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.resources.stringResource

@Composable
fun NetworksScreen(
    onNavigateUp: () -> Unit,
    onOpen: (NetworkEntity.Id) -> Unit,
    onAdd: () -> Unit,
) {
    KoshScaffold(
        title = { Text(stringResource(Res.string.networks_title)) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd.single(),
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        onNavigateUp = { onNavigateUp() }
    ) { paddingValues ->
        val networks = rememberNetworks()
        val toggleNetwork = rememberToggleNetwork()

        NetworksContent(
            init = networks.init,
            networks = networks.networks,
            enabled = networks.enabled,
            onSelect = { onOpen(it.id) },
            onToggle = { network, enabled -> toggleNetwork.toggle(network.id, enabled) },
            contentPadding = paddingValues,
        )
    }
}

@Composable
fun NetworksContent(
    init: Boolean,
    networks: ImmutableList<NetworkEntity>,
    enabled: ImmutableSet<NetworkEntity.Id>,
    onSelect: (NetworkEntity) -> Unit,
    onToggle: (NetworkEntity, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = modifier.fillMaxSize(),
    ) {

        when {
            !init -> networks(
                networks = List(5) { null }.toPersistentList(),
                enabled = enabled,
                onSelect = onSelect,
                onToggle = onToggle
            )

            networks.isEmpty() -> {
                item {
                    EmptyNetworksContent()
                }
            }

            else -> networks(
                networks = networks,
                enabled = enabled,
                onSelect = onSelect,
                onToggle = onToggle
            )
        }

        item {
            Spacer(Modifier.height(128.dp))
        }
    }
}

private fun LazyListScope.networks(
    networks: ImmutableList<NetworkEntity?>,
    enabled: ImmutableSet<NetworkEntity.Id>,
    onSelect: (NetworkEntity) -> Unit,
    onToggle: (NetworkEntity, Boolean) -> Unit,
) {
    items(
        count = networks.size,
        key = { networks[it]?.id?.value?.leastSignificantBits ?: it }
    ) { index ->
        val network = networks[index]

        NetworkItem(
            modifier = Modifier.animateItem(),
            network = network,
            onClick = { network?.let { onSelect(it) } },
            trailingContent = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    VerticalDivider(
                        modifier = Modifier
                            .height(32.dp)
                            .placeholder(network == null),
                    )

                    Switch(
                        enabled = network?.chainId != ethereum,
                        checked = network?.id in enabled,
                        onCheckedChange = { checked -> network?.let { onToggle(it, checked) } },
                        modifier = Modifier.placeholder(network == null),
                    )
                }
            }
        )
    }
}

@Composable
private fun EmptyNetworksContent() {
    Illustration(
        NetworksEmpty(),
        "NetworksEmpty",
        Modifier
            .fillMaxWidth()
            .padding(64.dp),
    ) {
        Text(
            "Get started by adding your first network",
            textAlign = TextAlign.Center
        )
    }
}
