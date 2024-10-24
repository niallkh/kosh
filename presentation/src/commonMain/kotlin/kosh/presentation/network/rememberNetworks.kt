package kosh.presentation.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kosh.domain.entities.NetworkEntity
import kosh.domain.serializers.ImmutableList
import kosh.domain.serializers.ImmutableSet
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.activeNetworksIds
import kosh.domain.state.networks
import kosh.domain.utils.optic
import kosh.presentation.core.di

@Composable
fun rememberNetworks(
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): NetworksState {
    val networks by appStateProvider.collectAsState().optic(AppState.networks())
    val enabled by appStateProvider.collectAsState().optic(AppState.activeNetworksIds())

    return NetworksState(
        networks = networks,
        enabled = enabled,
        init = appStateProvider.init,
    )
}

@Immutable
data class NetworksState(
    val networks: ImmutableList<NetworkEntity>,
    val enabled: ImmutableSet<NetworkEntity.Id>,
    val init: Boolean,
)
