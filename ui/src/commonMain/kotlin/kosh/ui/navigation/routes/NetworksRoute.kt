package kosh.ui.navigation.routes

import kosh.domain.entities.NetworkEntity
import kotlinx.serialization.Serializable

@Serializable
sealed class NetworksRoute : Route {

    @Serializable
    data object List : NetworksRoute()

    @Serializable
    data class Details(val id: NetworkEntity.Id?) : NetworksRoute()

    @Serializable
    data class Delete(val id: NetworkEntity.Id) : NetworksRoute()
}
