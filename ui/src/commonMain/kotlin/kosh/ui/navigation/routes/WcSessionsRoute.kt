package kosh.ui.navigation.routes

import kosh.domain.models.ChainAddress
import kosh.domain.models.at
import kosh.domain.models.reown.PairingUri
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.reown.WcRequest.Call
import kosh.domain.models.reown.WcSession
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.serializers.BigInteger
import kosh.ui.navigation.Deeplink
import kotlinx.serialization.Serializable

@Serializable
sealed class WcSessionsRoute : Route {

    @Serializable
    data class Session(val id: WcSession.Id) : WcSessionsRoute()

    @Serializable
    data class Pair(
        val uri: PairingUri? = null,
        override val link: Deeplink? = null,
    ) : WcSessionsRoute()

    @Serializable
    data class Proposal(
        val id: WcSessionProposal.Id,
        val requestId: Long,
        override val link: Deeplink? = null,
    ) : WcSessionsRoute()

    @Serializable
    data class Auth(
        val id: WcAuthentication.Id,
        override val link: Deeplink? = null,
    ) : WcSessionsRoute()

    @Serializable
    data class Request(
        val id: WcRequest.Id?,
        override val link: Deeplink? = null,
    ) : WcSessionsRoute() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as Request

            if (id != other.id) return false
            if (link != other.link) return false
            if (id == null || other.id == null) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id?.hashCode() ?: 0
            result = 31 * result + (link?.hashCode() ?: 0)
            return result
        }
    }

    @Serializable
    data class PersonalSign(
        val id: WcRequest.Id,
        override val link: Deeplink? = null,
    ) : WcSessionsRoute()

    @Serializable
    data class SignTypedData(
        val id: WcRequest.Id,
        override val link: Deeplink? = null,
    ) : WcSessionsRoute()

    @Serializable
    data class SendTransaction(
        val id: WcRequest.Id,
        override val link: Deeplink? = null,
    ) : WcSessionsRoute()

    @Serializable
    data class AddEthereumNetwork(
        val id: WcRequest.Id,
        override val link: Deeplink? = null,
    ) : WcSessionsRoute()

    @Serializable
    data class WatchToken(
        val id: WcRequest.Id,
        val chainAddress: ChainAddress,
        override val link: Deeplink? = null,
    ) : WcSessionsRoute()

    @Serializable
    data class WatchNft(
        val id: WcRequest.Id,
        val chainAddress: ChainAddress,
        val tokenId: BigInteger?,
        override val link: Deeplink? = null,
    ) : WcSessionsRoute()
}

fun wcRequestRoute(request: WcRequest): WcSessionsRoute = when (val call = request.call) {
    is Call.SignPersonal -> WcSessionsRoute.PersonalSign(request.id)
    is Call.SignTyped -> WcSessionsRoute.SignTypedData(request.id)
    is Call.SendTransaction -> WcSessionsRoute.SendTransaction(request.id)
    is Call.AddNetwork -> WcSessionsRoute.AddEthereumNetwork(request.id)
    is Call.WatchAsset -> when (val tokenId = call.tokenId) {
        null -> WcSessionsRoute.WatchToken(request.id, call.chainId.at(call.address))
        else -> WcSessionsRoute.WatchNft(request.id, call.chainId.at(call.address), tokenId)
    }
}
