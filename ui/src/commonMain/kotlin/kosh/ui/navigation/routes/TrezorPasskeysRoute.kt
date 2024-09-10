package kosh.ui.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
sealed class TrezorPasskeysRoute : Route {

    @Serializable
    data object List : TrezorPasskeysRoute()
}
