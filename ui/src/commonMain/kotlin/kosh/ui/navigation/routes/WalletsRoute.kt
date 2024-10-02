package kosh.ui.navigation.routes

import kosh.domain.entities.AccountEntity
import kosh.domain.models.hw.HardwareWallet
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
    data class NewAccount(val hw: HardwareWallet) : WalletsRoute()

    @Serializable
    data class TokensDiscovery(val id: AccountEntity.Id) : WalletsRoute()
}
