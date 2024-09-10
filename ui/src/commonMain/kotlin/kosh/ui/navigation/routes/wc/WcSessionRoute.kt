package kosh.ui.navigation.routes.wc

import kosh.domain.models.wc.WcSession
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.Serializable

@Serializable
sealed class WcSessionRoute : Route {

    @Serializable
    data class Session(val id: WcSession.Id) : WcSessionRoute()
}
