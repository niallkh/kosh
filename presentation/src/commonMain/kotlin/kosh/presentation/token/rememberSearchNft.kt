package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ChainAddress
import kosh.domain.models.Uri
import kosh.domain.models.token.NftMetadata
import kosh.domain.models.token.TokenMetadata
import kosh.domain.usecases.token.TokenDiscoveryService
import kosh.presentation.di.di

@Composable
fun rememberSearchNft(
    chainAddress: ChainAddress,
    query: String,
    icon: Pair<Address, Uri>? = null,
    tokenDiscoveryService: TokenDiscoveryService = di { domain.tokenDiscoveryService },
): SearchNftState {
    var token by remember { mutableStateOf<TokenMetadata?>(null) }
    var nft by remember { mutableStateOf<NftMetadata?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<Web3Failure?>(null) }
    var retry by remember { mutableIntStateOf(0) }

    LaunchedEffect(retry, query) {
        loading = true

        recover({
            token = token ?: tokenDiscoveryService.getTokenMetadata(
                NetworkEntity.Id(chainAddress.chainId), chainAddress.address
            ).bind()

            nft = tokenDiscoveryService.searchNft(token ?: raise(null), query).bind()

            loading = false
        }) {
            failure = it
            loading = false
        }
    }

    LaunchedEffect(icon, token) {
        if (token != null
            && token?.address == icon?.first
            && token?.icon != icon?.second
        ) {
            token = token?.copy(icon = icon?.second)
        }
    }

    return SearchNftState(
        token = token,
        nft = nft,
        loading = loading,
        failure = failure,
        retry = { retry++ }
    )
}

@Stable
data class SearchNftState(
    val token: TokenMetadata?,
    val nft: NftMetadata?,
    val loading: Boolean,
    val failure: Web3Failure?,
    val retry: () -> Unit,
)
