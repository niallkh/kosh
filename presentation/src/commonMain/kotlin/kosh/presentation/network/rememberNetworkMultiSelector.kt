package kosh.presentation.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.optics.Getter
import kosh.domain.entities.NetworkEntity
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.activeNetworks
import kosh.domain.utils.optic
import kosh.presentation.di.di
import kosh.presentation.di.rememberSerializable
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentHashSet


@Composable
fun rememberNetworkMultiSelector(
    initial: Getter<AppState, PersistentList<NetworkEntity>> = AppState.activeNetworks(),
    initialRequired: Set<NetworkEntity.Id> = setOf(),
    initialSelected: Set<NetworkEntity.Id> = setOf(),
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): NetworkMultiSelectorState {
    val networks by appStateProvider.collectAsState().optic(initial)
    val required by rememberSerializable { mutableStateOf(initialRequired) }
    var selected by rememberSerializable { mutableStateOf(initialSelected + initialRequired) }

    return NetworkMultiSelectorState(
        selected = selected,
        required = required,
        available = networks,
        select = {
            if (it !in required) {
                selected = if (it !in selected) {
                    selected.toPersistentHashSet().add(it)
                } else {
                    selected.toPersistentHashSet().remove(it)
                }
            }
        },
    )
}

@Immutable
data class NetworkMultiSelectorState(
    val selected: Set<NetworkEntity.Id>,
    val required: Set<NetworkEntity.Id>,
    val available: List<NetworkEntity>,
    val select: (NetworkEntity.Id) -> Unit,
)
