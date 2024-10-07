package kosh.ui.navigation.routes.wc

import kosh.domain.models.ChainAddress
import kosh.domain.models.at
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.reown.WcRequest.Call
import kosh.domain.serializers.BigInteger
import kosh.ui.navigation.Deeplink
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.Serializable

@Serializable
sealed class WcRequestRoute : Route {

    @Serializable
    data class Request(
        val id: WcRequest.Id?,
        override val link: Deeplink? = null,
    ) : WcRequestRoute() {

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
    ) : WcRequestRoute()

    @Serializable
    data class SignTypedData(
        val id: WcRequest.Id,
        override val link: Deeplink? = null,
    ) : WcRequestRoute()

    @Serializable
    data class SendTransaction(
        val id: WcRequest.Id,
        override val link: Deeplink? = null,
    ) : WcRequestRoute()

    @Serializable
    data class AddEthereumNetwork(
        val id: WcRequest.Id,
        override val link: Deeplink? = null,
    ) : WcRequestRoute()

    @Serializable
    data class WatchToken(
        val id: WcRequest.Id,
        val chainAddress: ChainAddress,
        override val link: Deeplink? = null,
    ) : WcRequestRoute()

    @Serializable
    data class WatchNft(
        val id: WcRequest.Id,
        val chainAddress: ChainAddress,
        val tokenId: BigInteger?,
        override val link: Deeplink? = null,
    ) : WcRequestRoute()
}

fun wcRequestRoute(request: WcRequest): WcRequestRoute = when (val call = request.call) {
    is Call.SignPersonal -> WcRequestRoute.PersonalSign(request.id)
    is Call.SignTyped -> WcRequestRoute.SignTypedData(request.id)
    is Call.SendTransaction -> WcRequestRoute.SendTransaction(request.id)
    is Call.AddNetwork -> WcRequestRoute.AddEthereumNetwork(request.id)
    is Call.WatchAsset -> when (val tokenId = call.tokenId) {
        null -> WcRequestRoute.WatchToken(request.id, call.chainId.at(call.address))
        else -> WcRequestRoute.WatchNft(request.id, call.chainId.at(call.address), tokenId)
    }
}

