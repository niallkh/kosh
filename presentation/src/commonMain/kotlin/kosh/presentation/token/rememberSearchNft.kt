package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.models.ChainAddress
import kosh.domain.models.token.NftMetadata
import kosh.domain.models.token.TokenMetadata
import kosh.domain.usecases.token.TokenDiscoveryService
import kosh.presentation.core.di
import kosh.presentation.rememberLoad

@Composable
fun rememberSearchNft(
    chainAddress: ChainAddress,
    query: String,
    tokenDiscoveryService: TokenDiscoveryService = di { domain.tokenDiscoveryService },
): SearchNftState {
    val nft = rememberLoad(chainAddress, query) {
        val token = tokenDiscoveryService.getTokenMetadata(
            NetworkEntity.Id(chainAddress.chainId), chainAddress.address
        ).bind()

        val nft = tokenDiscoveryService.searchNft(token ?: raise(null), query).bind()

        token to nft
    }

    return remember {
        object : SearchNftState {
            override val token: TokenMetadata? get() = nft.result?.first
            override val nft: NftMetadata? get() = nft.result?.second
            override val loading: Boolean get() = nft.loading
            override val failure: Web3Failure? get() = nft.failure

            override fun retry() {
                nft.retry()
            }
        }
    }
}

@Stable
interface SearchNftState {
    val token: TokenMetadata?
    val nft: NftMetadata?
    val loading: Boolean
    val failure: Web3Failure?
    fun retry()
}
