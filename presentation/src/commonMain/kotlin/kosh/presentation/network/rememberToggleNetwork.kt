package kosh.presentation.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.entities.NetworkEntity
import kosh.domain.usecases.network.NetworkService
import kosh.presentation.Perform
import kosh.presentation.core.di

@Composable
fun rememberToggleNetwork(
    networkService: NetworkService = di { domain.networkService },
): ToggleNetworkState {
    val toggle = Perform<_, _, Any> { (id, enabled): Pair<NetworkEntity.Id, Boolean> ->
        networkService.toggle(id, enabled)
    }

    return ToggleNetworkState(
        toggle = { id, enabled ->
            toggle(id to enabled)
        },
        toggled = toggle.performed,
    )
}

@Immutable
data class ToggleNetworkState(
    val toggle: (NetworkEntity.Id, Boolean) -> Unit,
    val toggled: Boolean,
)
