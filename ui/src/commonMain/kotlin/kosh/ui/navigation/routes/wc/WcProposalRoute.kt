package kosh.ui.navigation.routes.wc

import kosh.domain.models.wc.PairingUri
import kosh.domain.models.wc.WcAuthentication
import kosh.domain.models.wc.WcSessionProposal
import kosh.ui.navigation.Deeplink
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.Serializable

@Serializable
sealed class WcProposalRoute : Route {

    @Serializable
    data class Pair(
        val uri: PairingUri? = null,
        override val link: Deeplink? = null,
    ) : WcProposalRoute()

    @Serializable
    data class Proposal(
        val id: WcSessionProposal.Id,
        val requestId: Long,
        override val link: Deeplink? = null,
    ) : WcProposalRoute()

    @Serializable
    data class Auth(
        val id: WcAuthentication.Id,
        override val link: Deeplink? = null,
    ) : WcProposalRoute()
}
