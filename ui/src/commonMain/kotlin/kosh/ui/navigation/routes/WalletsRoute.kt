package kosh.ui.navigation.routes

import kosh.domain.entities.AccountEntity
import kotlinx.serialization.Serializable

@Serializable
sealed class WalletsRoute : Route {

    @Serializable
    data object List : WalletsRoute()

    @Serializable
    data class Account(val id: AccountEntity.Id) : WalletsRoute()

    @Serializable
    data class DeleteAccount(val id: AccountEntity.Id) : WalletsRoute()

    @Serializable
    data object NewTrezorAccount : WalletsRoute()

    @Serializable
    data object NewLedgerAccount : WalletsRoute()

    @Serializable
    data class TokensDiscovery(val id: AccountEntity.Id) : WalletsRoute()
}
