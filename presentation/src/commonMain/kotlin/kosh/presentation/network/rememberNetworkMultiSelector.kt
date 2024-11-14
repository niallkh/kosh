package kosh.presentation.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import kosh.domain.entities.NetworkEntity
import kosh.domain.serializers.ImmutableSetSerializer
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.networks
import kosh.domain.utils.optic
import kosh.presentation.component.selector.selector
import kosh.presentation.core.di
import kosh.presentation.di.rememberSerializable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.minus
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.plus
import kotlinx.collections.immutable.toPersistentHashSet
import kotlinx.collections.immutable.toPersistentSet


@Composable
fun rememberNetworkMultiSelector(
    networkIds: ImmutableSet<NetworkEntity.Id>,
    requiredIds: ImmutableSet<NetworkEntity.Id>,
    selectedIds: ImmutableSet<NetworkEntity.Id> = persistentSetOf(),
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): NetworkMultiSelectorState {
    val networks by optic(AppState.networks(networkIds)) { appStateProvider.state }
    val required by rememberUpdatedState(requiredIds)
    var selected by rememberSerializable(
        stateSerializer = ImmutableSetSerializer(NetworkEntity.Id.serializer())
    ) {
        mutableStateOf(selectedIds)
    }

    selector(selectedIds, ImmutableSetSerializer(NetworkEntity.Id.serializer())) {
        selected = selected.toPersistentSet() + it
    }

    return remember {
        object : NetworkMultiSelectorState {
            override val selected: ImmutableSet<NetworkEntity.Id> get() = selected
            override val required: ImmutableSet<NetworkEntity.Id> get() = required
            override val networks: ImmutableList<NetworkEntity> get() = networks

            override fun invoke(id: NetworkEntity.Id) {
                if (id !in required) {
                    selected = if (id !in selected) {
                        selected.toPersistentHashSet() + id
                    } else {
                        selected.toPersistentHashSet() - id
                    }
                }
            }
        }
    }
}

@Stable
interface NetworkMultiSelectorState {
    val networks: ImmutableList<NetworkEntity>
    val selected: ImmutableSet<NetworkEntity.Id>
    val required: ImmutableSet<NetworkEntity.Id>
    operator fun invoke(id: NetworkEntity.Id)
}
