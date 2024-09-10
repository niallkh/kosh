package kosh.presentation.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.AppFailure
import kosh.domain.usecases.network.NetworkService
import kosh.presentation.PerformAction
import kosh.presentation.di.di

@Composable
fun rememberToggleNetwork(
    networkService: NetworkService = di { domain.networkService },
): ToggleNetworkState {
    val toggle = PerformAction<Pair<NetworkEntity.Id, Boolean>, AppFailure> { (id, enabled) ->
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
