package kosh.ui.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute : Route {

    @Serializable
    data class Assets(
        override val link: RootRoute? = null,
    ) : HomeRoute

    @Serializable
    data class Activity(
        override val link: RootRoute? = null,
    ) : HomeRoute

    @Serializable
    data class WalletConnect(
        override val link: RootRoute? = null,
    ) : HomeRoute
}
