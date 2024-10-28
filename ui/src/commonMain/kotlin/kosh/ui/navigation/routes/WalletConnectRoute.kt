package kosh.ui.navigation.routes

import kosh.domain.models.reown.PairingUri
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.reown.WcSession
import kosh.domain.models.reown.WcSessionProposal
import kosh.ui.navigation.Deeplink
import kotlinx.serialization.Serializable

@Serializable
sealed class WalletConnectRoute : Route {

    @Serializable
    data object List : WalletConnectRoute()

    @Serializable
    data class Session(val id: WcSession.Id) : WalletConnectRoute()

    @Serializable
    data class Pair(
        val uri: PairingUri? = null,
        override val link: Deeplink? = null,
    ) : WalletConnectRoute()

    @Serializable
    data class Proposal(
        val id: WcSessionProposal.Id,
        val requestId: Long,
        override val link: Deeplink? = null,
    ) : WalletConnectRoute()

    @Serializable
    data class Auth(
        val id: WcAuthentication.Id,
        override val link: Deeplink? = null,
    ) : WalletConnectRoute()
}
