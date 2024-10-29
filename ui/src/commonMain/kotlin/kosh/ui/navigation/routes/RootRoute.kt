package kosh.ui.navigation.routes

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.uuid.Uuid

@Serializable
sealed class RootRoute : Route {

    @Serializable
    data class Tokens(
        override val link: TokensRoute? = null,
        val isNft: Boolean = false,
        @Transient
        private val uuid: Uuid = Uuid.random(),
    ) : RootRoute()

    @Serializable
    data class Transactions(
        override val link: TransactionsRoute? = null,
        @Transient
        private val uuid: Uuid = Uuid.random(),
    ) : RootRoute()

    @Serializable
    data class WalletConnect(
        override val link: WalletConnectRoute? = null,
        @Transient
        private val uuid: Uuid = Uuid.random(),
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
    data class TrezorPasskeys(
        override val link: TrezorPasskeysRoute? = null,
    ) : RootRoute()
}
