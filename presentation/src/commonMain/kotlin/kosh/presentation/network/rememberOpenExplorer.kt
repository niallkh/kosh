package kosh.presentation.network

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import kosh.domain.entities.NetworkEntity
import kosh.domain.models.Address
import kosh.domain.models.Hash

@Composable
fun rememberOpenExplorer(
    id: NetworkEntity.Id,
): OpenExplorerState {
    val network = rememberNetwork(id)

    val uriHandler = LocalUriHandler.current

    return OpenExplorerState(
        openTransaction = { hash ->
            network.entity?.explorers?.firstOrNull()?.let {
                uriHandler.openUri("$it/tx/$hash")
            }
        },
        openAddress = { address ->
            network.entity?.explorers?.firstOrNull()?.let {
                uriHandler.openUri("$it/address/$address")
            }
        },
        openToken = { address ->
            network.entity?.explorers?.firstOrNull()?.let {
                uriHandler.openUri("$it/token/$address")
            }
        }
    )
}

data class OpenExplorerState(
    val openTransaction: (Hash) -> Unit,
    val openAddress: (Address) -> Unit,
    val openToken: (Address) -> Unit,
)
