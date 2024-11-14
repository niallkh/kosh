package kosh.presentation.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kosh.domain.entities.NetworkEntity
import kosh.domain.serializers.ImmutableList
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.activeNetworks
import kosh.domain.utils.optic
import kosh.presentation.core.di
import kosh.presentation.di.rememberSerializable

@Composable
fun rememberNetworkSelector(
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): NetworkSelectorState {
    val accounts by optic(AppState.activeNetworks()) { appStateProvider.state }
    var selected by rememberSerializable { mutableStateOf(accounts.firstOrNull()) }

    return remember {
        object : NetworkSelectorState {
            override val selected: NetworkEntity? get() = selected
            override val available: ImmutableList<NetworkEntity> get() = accounts
            override fun select(network: NetworkEntity) {
                selected = network
            }
        }
    }
}

@Stable
interface NetworkSelectorState {
    val selected: NetworkEntity?
    val available: ImmutableList<NetworkEntity>
    fun select(network: NetworkEntity)
}

