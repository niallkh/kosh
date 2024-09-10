package kosh.datastore.migrations

import arrow.optics.dsl.at
import arrow.optics.typeclasses.At
import com.eygraber.uri.Uri
import kosh.domain.entities.NetworkEntity
import kosh.domain.models.ChainId
import kosh.domain.state.AppState
import kosh.domain.state.enabledNetworkIds
import kosh.domain.state.networks
import kosh.domain.utils.Copy
import kosh.domain.utils.phset
import kosh.domain.utils.pmap
import kosh.ui.resources.Icons
import kotlinx.collections.immutable.toPersistentList

internal fun Copy<AppState>.network(
    chainId: ChainId,
    name: String,
    testnet: Boolean = false,
    enabled: Boolean = false,
    readRpcProvider: Uri,
    writeRpcProvider: Uri? = null,
    explorers: List<String>,
    icon: String,
) {
    val network = NetworkEntity(
        chainId = chainId,
        name = name,
        readRpcProvider = readRpcProvider,
        writeRpcProvider = writeRpcProvider,
        explorers = explorers.map { Uri.parse(it) }.toPersistentList(),
        testnet = testnet,
        icon = Icons.icon(icon.lowercase()),
    )

    AppState.networks.at(At.pmap(), network.id) set network
    AppState.enabledNetworkIds.at(At.phset(), network.id) set enabled
}
