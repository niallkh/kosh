package kosh.ui.navigation.routes

import kosh.domain.models.Address
import kosh.domain.models.ChainAddress
import kosh.domain.serializers.BigInteger
import kosh.ui.navigation.Deeplink
import kotlinx.serialization.Serializable

@Serializable
sealed class AddTokenRoute : Route {

    @Serializable
    data class Search(
        val address: Address? = null,
        override val link: Deeplink? = null,
    ) : AddTokenRoute()

    @Serializable
    data class NftSearch(
        val chainAddress: ChainAddress,
        val tokenId: BigInteger? = null,
        override val link: Deeplink? = null,
    ) : AddTokenRoute()
}
