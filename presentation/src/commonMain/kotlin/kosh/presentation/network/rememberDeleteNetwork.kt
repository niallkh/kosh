package kosh.presentation.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.entities.NetworkEntity
import kosh.domain.usecases.network.NetworkService
import kosh.presentation.core.di
import kosh.presentation.rememberEffect

@Composable
fun rememberDeleteNetwork(
    id: NetworkEntity.Id,
    networkService: NetworkService = di { domain.networkService },
): DeleteNetworkState {

    val delete = rememberEffect(id) {
        networkService.delete(id)
    }

    return DeleteNetworkState(
        deleted = delete.done,
        deleting = delete.inProgress,
        delete = { delete() }
    )
}

@Immutable
data class DeleteNetworkState(
    val deleted: Boolean,
    val deleting: Boolean,
    val delete: () -> Unit,
)
