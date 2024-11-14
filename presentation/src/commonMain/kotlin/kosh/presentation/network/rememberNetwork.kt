package kosh.presentation.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kosh.domain.entities.NetworkEntity
import kosh.domain.models.ChainId
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.isActive
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
): NetworkState {
    val network by optic(AppState.network(id)) { appStateProvider.state }
    val active by optic(AppState.isActive(id)) { appStateProvider.state }

    return remember {
        object : NetworkState {
            override val entity: NetworkEntity? get() = network
            override val active: Boolean get() = active
        }
    }
}

@Stable
interface NetworkState {
    val entity: NetworkEntity?
    val active: Boolean
}
