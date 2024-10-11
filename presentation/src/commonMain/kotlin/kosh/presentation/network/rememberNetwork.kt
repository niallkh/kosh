package kosh.presentation.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kosh.domain.entities.NetworkEntity
import kosh.domain.models.ChainId
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.activeNetworks
import kosh.domain.state.network
import kosh.domain.utils.optic
import kosh.presentation.core.di

@Composable
fun rememberNetwork(
    chainId: ChainId,
) = rememberNetwork(NetworkEntity.Id(chainId))

@Composable
fun rememberNetwork(
    id: NetworkEntity.Id,
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): Network {
    val network by appStateProvider.collectAsState().optic(AppState.network(id))
    val activeNetworks by appStateProvider.collectAsState().optic(AppState.activeNetworks())
    val enabled by remember { derivedStateOf { network?.id in activeNetworks.map { it.id } } }

    return Network(
        entity = network,
        enabled = enabled,
    )
}

@Immutable
data class Network(
    val entity: NetworkEntity?,
    val enabled: Boolean,
)
