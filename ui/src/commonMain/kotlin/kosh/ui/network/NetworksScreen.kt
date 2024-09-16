package kosh.ui.network

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import kosh.domain.serializers.ImmutableList
import kosh.domain.serializers.ImmutableSet
import kosh.presentation.network.rememberNetworks
import kosh.presentation.network.rememberToggleNetwork
import kosh.ui.component.illustration.Illustration
import kosh.ui.component.network.NetworkItem
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.single.single
import kosh.ui.resources.Res
import kosh.ui.resources.illustrations.NetworksEmpty
import kosh.ui.resources.networks_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun NetworksScreen(
    onNavigateUp: () -> Unit,
    onOpen: (NetworkEntity.Id) -> Unit,
    onAdd: () -> Unit,
) {
    val networks = rememberNetworks()
    val toggleNetwork = rememberToggleNetwork()

    NetworksContent(
        networks = networks.networks,
        enabled = networks.enabled,
        onSelect = { onOpen(it.id) },
        onAdd = onAdd,
        onNavigateUp = onNavigateUp,
        onToggle = { network, enabled -> toggleNetwork.toggle(network.id, enabled) }
    )
}

@Composable
fun NetworksContent(
    networks: ImmutableList<NetworkEntity>,
    enabled: ImmutableSet<NetworkEntity.Id>,
    onSelect: (NetworkEntity) -> Unit,
    onToggle: (NetworkEntity, Boolean) -> Unit,
    onAdd: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    KoshScaffold(
        modifier = modifier,
        title = { Text(stringResource(Res.string.networks_title)) },

        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd.single(),
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        onUp = { onNavigateUp() }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
        ) {

            if (networks.isEmpty()) {
                item {
                    Illustration(
                        NetworksEmpty(),
                        "NetworksEmpty",
                        Modifier
                            .fillMaxWidth()
                            .padding(64.dp),
                    ) {
                        Text(
                            "Get started by adding new Ethereum Network",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            items(
                items = networks,
                key = { it.id.value.leastSignificantBits }
            ) { network ->

                NetworkItem(
                    modifier = Modifier.animateItemPlacement(),
                    network = network,
                    onClick = { onSelect(network) },
                    trailingContent = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            VerticalDivider(
                                modifier = Modifier.height(32.dp),
                            )

                            Switch(
                                checked = network.id in enabled,
                                onCheckedChange = { onToggle(network, it) },
                            )
                        }
                    }
                )
            }

            item {
                Spacer(Modifier.height(64.dp))
            }
        }
    }
}
