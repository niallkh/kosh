package kosh.presentation.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.AppFailure
import kosh.domain.usecases.network.NetworkService
import kosh.presentation.PerformAction
import kosh.presentation.di.di

@Composable
fun rememberDeleteNetwork(
    id: NetworkEntity.Id,
    networkService: NetworkService = di { domain.networkService },
): DeleteNetworkState {

    val delete = PerformAction<Unit, AppFailure>(id) {
        networkService.delete(id)
    }

    return DeleteNetworkState(
        deleted = delete.performed,
        deleting = delete.inProgress,
        delete = { delete(Unit) }
    )
}

@Immutable
data class DeleteNetworkState(
    val deleted: Boolean,
    val deleting: Boolean,
    val delete: () -> Unit,
)
