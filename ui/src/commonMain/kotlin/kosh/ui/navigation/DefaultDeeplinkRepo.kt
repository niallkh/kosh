package kosh.ui.navigation

import kosh.domain.models.Uri
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.repositories.DeeplinkRepo
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.routes.wc.WcProposalRoute
import kosh.ui.navigation.routes.wc.WcRequestRoute

class DefaultDeeplinkRepo : DeeplinkRepo {
    override fun wcProposal(proposal: WcSessionProposal): Uri {
        return deeplink(
            RootRoute.WcProposal(WcProposalRoute.Proposal(proposal.id, proposal.requestId))
        )
    }

    override fun wcAuthentication(authentication: WcAuthentication): Uri {
        return deeplink(RootRoute.WcProposal(WcProposalRoute.Auth(authentication.id)))
    }

    override fun wcRequest(request: WcRequest): Uri {
        return deeplink(RootRoute.WcRequest(WcRequestRoute.Request(request.id)))
    }
}
