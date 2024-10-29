package kosh.ui.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
sealed class RootRoute : Route {

    @Serializable
    data class Tokens(
        override val link: TokensRoute? = null,
        val isNft: Boolean = false,
    ) : RootRoute()

    @Serializable
    data class Transactions(
        override val link: TransactionsRoute? = null,
    ) : RootRoute()

    @Serializable
    data class WalletConnect(
        override val link: WalletConnectRoute? = null,
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
