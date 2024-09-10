package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.serializers.BigInteger
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.activeTokens
import kosh.domain.state.token
import kosh.domain.utils.optic
import kosh.presentation.di.di

@Composable
fun rememberNativeToken(
    id: NetworkEntity.Id,
) = rememberToken(TokenEntity.Id(id))

@Composable
fun rememberNativeToken(
    chainId: ChainId,
) = rememberToken(TokenEntity.Id(NetworkEntity.Id(chainId)))

@Composable
fun rememberToken(
    chainId: ChainId,
    address: Address,
    tokenId: BigInteger? = null,
) = rememberToken(TokenEntity.Id(NetworkEntity.Id(chainId), address, tokenId))

@Composable
fun rememberToken(
    id: TokenEntity.Id,
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): TokenState {
    val token by appStateProvider.collectAsState().optic(AppState.token(id))
    val activeTokens by appStateProvider.collectAsState().optic(AppState.activeTokens())
    val enabled by remember { derivedStateOf { token?.id in activeTokens.map { it.id } } }

    return TokenState(
        entity = token,
        enabled = enabled,
    )
}

@Immutable
data class TokenState(
    val entity: TokenEntity?,
    val enabled: Boolean,
)
