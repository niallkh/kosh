package kosh.ui.navigation.routes

import kosh.domain.entities.AccountEntity
import kosh.ui.navigation.Deeplink
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
    data class NewTrezorAccount(
        override val link: Deeplink? = null,
    ) : WalletsRoute()

    @Serializable
    data class NewLedgerAccount(
        override val link: Deeplink? = null,
    ) : WalletsRoute()
}
