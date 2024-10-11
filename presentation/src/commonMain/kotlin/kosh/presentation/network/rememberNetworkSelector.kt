package kosh.presentation.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
    val accounts by appStateProvider.collectAsState().optic(AppState.activeNetworks())
    var selected by rememberSerializable { mutableStateOf(accounts.firstOrNull()) }

    return NetworkSelectorState(
        selected = selected,
        available = accounts,
        select = { selected = it }
    )
}

@Immutable
data class NetworkSelectorState(
    val selected: NetworkEntity?,
    val available: ImmutableList<NetworkEntity>,
    val select: (NetworkEntity) -> Unit,
)
