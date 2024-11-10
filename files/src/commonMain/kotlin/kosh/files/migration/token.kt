package kosh.files.migration

import arrow.optics.dsl.at
import arrow.optics.typeclasses.At
import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.models.ChainId
import kosh.domain.state.AppState
import kosh.domain.state.tokens
import kosh.domain.utils.Copy
import kosh.domain.utils.pmap
import kosh.ui.resources.Icons

internal fun Copy<AppState>.token(
    chainId: ChainId,
    name: String,
    symbol: String,
    decimals: UByte,
    icon: String = symbol.lowercase(),
) {
    val token = TokenEntity(
        networkId = NetworkEntity.Id(chainId),
        name = name,
        symbol = symbol,
        decimals = decimals,
        icon = Icons.icon(icon),
        type = TokenEntity.Type.Native
    )

    AppState.tokens.at(At.pmap(), token.id) set token
}
