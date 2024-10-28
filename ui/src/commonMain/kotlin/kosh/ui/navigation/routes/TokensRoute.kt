package kosh.ui.navigation.routes

import kosh.domain.entities.TokenEntity
import kotlinx.serialization.Serializable

@Serializable
sealed class TokensRoute : Route {

    @Serializable
    data object List : TokensRoute()

    @Serializable
    data class Details(val id: TokenEntity.Id) : TokensRoute()

    @Serializable
    data class Delete(val id: TokenEntity.Id) : TokensRoute()
}
