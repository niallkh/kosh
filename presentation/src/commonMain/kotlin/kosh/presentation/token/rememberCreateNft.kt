package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import kosh.domain.entities.TokenEntity
import kosh.domain.failure.TokenFailure
import kosh.domain.models.token.NftMetadata
import kosh.domain.models.token.TokenMetadata
import kosh.domain.models.token.TokenMetadata.Type
import kosh.domain.models.token.map
import kosh.domain.usecases.token.TokenService
import kosh.presentation.di.di

@Composable
fun rememberCreateNft(
    tokenService: TokenService = di { domain.tokenService },
): CreateNftState {
    var token by remember { mutableStateOf<TokenMetadata?>(null) }
    var nft by remember { mutableStateOf<NftMetadata?>(null) }
    var created by remember { mutableStateOf<TokenEntity.Id?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<TokenFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }

    LaunchedEffect(retry, token, nft) {
        loading = true

        recover({
            val localToken = token ?: raise(null)
            val localNft = nft ?: raise(null)

            created = tokenService.add(
                chainId = localNft.chainId,
                address = localNft.address,
                name = localToken.name,
                symbol = localNft.symbol ?: localToken.symbol,
                decimals = localNft.decimals ?: localToken.decimals,
                icon = localToken.icon,
                type = localToken.type.map(),
                tokenName = localNft.name,
                tokenId = localNft.tokenId,
                uri = localNft.uri,
                image = localNft.image,
                external = localNft.external,
            ).bind()

            failure = null
            loading = false
        }) {
            created = null

            failure = it
            loading = false
        }
    }

    return CreateNftState(
        created = created,
        loading = loading,
        failure = failure,
        create = { tokenMetadata, nftMetadata ->
            require(tokenMetadata.type in arrayOf(Type.ERC721, Type.ERC1155))
            retry++
            token = tokenMetadata
            nft = nftMetadata
        },
        retry = { retry++ }
    )
}

data class CreateNftState(
    val created: TokenEntity.Id?,
    val loading: Boolean,
    val failure: TokenFailure?,
    val create: (TokenMetadata, NftMetadata) -> Unit,
    val retry: () -> Unit,
)
