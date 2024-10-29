package kosh.ui.navigation.routes

import kosh.domain.entities.TransactionEntity
import kosh.domain.models.ChainAddress
import kosh.domain.models.at
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.reown.WcRequest.Call
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.serializers.BigInteger
import kosh.ui.navigation.Deeplink
import kotlinx.serialization.Serializable

@Serializable
sealed class TransactionsRoute : Route {

    @Serializable
    data object List : TransactionsRoute()

    @Serializable
    data class Details(val id: TransactionEntity.Id) : TransactionsRoute()

    @Serializable
    data class Delete(val id: TransactionEntity.Id) : TransactionsRoute()

    @Serializable
    data class Proposal(
        val id: WcSessionProposal.Id,
        val requestId: Long,
        override val link: Deeplink? = null,
    ) : TransactionsRoute()

    @Serializable
    data class Auth(
        val id: WcAuthentication.Id,
        override val link: Deeplink? = null,
    ) : TransactionsRoute()

    @Serializable
    data class Request(
        val id: WcRequest.Id?,
        override val link: Deeplink? = null,
    ) : TransactionsRoute()

    @Serializable
    data class PersonalSign(
        val id: WcRequest.Id,
        override val link: Deeplink? = null,
    ) : TransactionsRoute()

    @Serializable
    data class SignTypedData(
        val id: WcRequest.Id,
        override val link: Deeplink? = null,
    ) : TransactionsRoute()

    @Serializable
    data class SendTransaction(
        val id: WcRequest.Id,
        override val link: Deeplink? = null,
    ) : TransactionsRoute()

    @Serializable
    data class AddEthereumNetwork(
        val id: WcRequest.Id,
        override val link: Deeplink? = null,
    ) : TransactionsRoute()

    @Serializable
    data class WatchToken(
        val id: WcRequest.Id,
        val chainAddress: ChainAddress,
        override val link: Deeplink? = null,
    ) : TransactionsRoute()

    @Serializable
    data class WatchNft(
        val id: WcRequest.Id,
        val chainAddress: ChainAddress,
        val tokenId: BigInteger?,
        override val link: Deeplink? = null,
    ) : TransactionsRoute()
}

fun wcRequestRoute(request: WcRequest): TransactionsRoute = when (val call = request.call) {
    is Call.SignPersonal -> TransactionsRoute.PersonalSign(request.id)
    is Call.SignTyped -> TransactionsRoute.SignTypedData(request.id)
    is Call.SendTransaction -> TransactionsRoute.SendTransaction(request.id)
    is Call.AddNetwork -> TransactionsRoute.AddEthereumNetwork(request.id)
    is Call.WatchAsset -> when (val tokenId = call.tokenId) {
        null -> TransactionsRoute.WatchToken(request.id, call.chainId.at(call.address))
        else -> TransactionsRoute.WatchNft(request.id, call.chainId.at(call.address), tokenId)
    }
}
