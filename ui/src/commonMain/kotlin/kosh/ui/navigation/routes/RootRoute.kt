package kosh.ui.navigation.routes

import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.models.reown.WcRequest
import kosh.ui.navigation.routes.wc.WcProposalRoute
import kosh.ui.navigation.routes.wc.WcRequestRoute
import kosh.ui.navigation.routes.wc.WcSessionRoute
import kotlinx.serialization.Serializable

@Serializable
sealed class RootRoute : Route {

    @Serializable
    data class Home(
        override val link: HomeRoute? = null,
    ) : RootRoute()

    @Serializable
    data class Wallets(
        override val link: WalletsRoute? = null,
    ) : RootRoute()

    @Serializable
    data class Networks(
        override val link: NetworksRoute? = null,
    ) : RootRoute()

    @Serializable
    data class AddToken(
        override val link: AddTokenRoute? = null,
    ) : RootRoute()

    @Serializable
    data class Tokens(
        override val link: TokensRoute,
        val isNft: Boolean = false,
    ) : RootRoute()

    @Serializable
    data class Transactions(
        override val link: TransactionsRoute,
    ) : RootRoute()

    @Serializable
    data class TrezorPasskeys(
        override val link: TrezorPasskeysRoute? = null,
    ) : RootRoute()

    @Serializable
    data class WcSession(
        override val link: WcSessionRoute,
    ) : RootRoute()

    @Serializable
    data class WcProposal(
        override val link: WcProposalRoute,
    ) : RootRoute()

    @Serializable
    data class WcRequest(
        override val link: WcRequestRoute,
    ) : RootRoute()
}

fun wcProposal(proposal: WcSessionProposal): RootRoute {
    return RootRoute.WcProposal(WcProposalRoute.Proposal(proposal.id, proposal.requestId))
}

fun wcAuthentication(authentication: WcAuthentication): RootRoute {
    return RootRoute.WcProposal(WcProposalRoute.Auth(authentication.id))
}

fun wcRequest(id: WcRequest.Id): RootRoute {
    return RootRoute.WcRequest(WcRequestRoute.Request(id))
}
