package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import kosh.domain.entities.TokenEntity
import kosh.domain.failure.TokenFailure
import kosh.domain.failure.Web3Failure
import kosh.domain.models.token.isNft
import kosh.domain.models.token.map
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.token
import kosh.domain.usecases.token.TokenDiscoveryService
import kosh.domain.usecases.token.TokenService
import kosh.domain.utils.optic
import kosh.presentation.core.di

@Composable
fun rememberRefreshToken(
    id: TokenEntity.Id,
    tokenDiscoveryService: TokenDiscoveryService = di { domain.tokenDiscoveryService },
    tokenService: TokenService = di { domain.tokenService },
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): RefreshTokenState {
    var loading by remember { mutableStateOf(false) }
    var web3Failure by remember { mutableStateOf<Web3Failure?>(null) }
    var tokenFailure by remember { mutableStateOf<TokenFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var refresh by remember { mutableStateOf(false) }

    LaunchedEffect(id, retry, refresh) {
        if (!refresh) return@LaunchedEffect
        loading = true

        recover({
            val token = appStateProvider.optic(AppState.token(id)).value ?: raise(null)

            val tokenMetadata = tokenDiscoveryService.getTokenMetadata(
                networkId = token.networkId,
                address = token.address ?: raise(null),
            ).bind() ?: raise(Web3Failure.NoOnChainData())

            val nftMetadata = if (tokenMetadata.isNft) {
                tokenDiscoveryService.getNftMetadata(
                    networkId = token.networkId,
                    address = token.address!!,
                    tokenId = token.tokenId!!,
                    type = tokenMetadata.type,
                    refresh = true
                ).bind()
            } else null

            recover({
                tokenService.update(
                    id = token.id,
                    name = tokenMetadata.name,
                    symbol = nftMetadata?.symbol ?: tokenMetadata.symbol,
                    decimals = nftMetadata?.decimals ?: tokenMetadata.decimals,
                    uri = nftMetadata?.uri,
                    tokenName = nftMetadata?.name,
                    type = tokenMetadata.type.map(),
                    external = nftMetadata?.external,
                    image = nftMetadata?.image,
                    icon = tokenMetadata.icon ?: token.icon,
                ).bind()

                refresh = false
                loading = false
            }) {
                tokenFailure = it
            }
        }) {
            loading = false
            web3Failure = it
        }
    }

    return RefreshTokenState(
        loading = loading,
        web3Failure = web3Failure,
        tokenFailure = tokenFailure,
        retry = { retry++ },
        refresh = {
            retry++
            refresh = true
        }
    )
}

@Immutable
data class RefreshTokenState(
    val loading: Boolean,
    val web3Failure: Web3Failure?,
    val tokenFailure: TokenFailure?,
    val retry: () -> Unit,
    val refresh: () -> Unit,
)
