package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.TokenFailure
import kosh.domain.models.Address
import kosh.domain.models.Uri
import kosh.domain.models.token.NftMetadata
import kosh.domain.models.token.TokenMetadata
import kosh.domain.models.token.map
import kosh.domain.usecases.token.TokenService
import kosh.presentation.core.di
import kosh.presentation.rememberEitherEffect

@Composable
fun rememberCreateNft(
    icon: Pair<Address, Uri>? = null,
    onCreated: () -> Unit,
    tokenService: TokenService = di { domain.tokenService },
): CreateNftState {
    val create = rememberEitherEffect(
        icon,
        onFinish = { onCreated() }
    ) { (token, nft): Pair<TokenMetadata, NftMetadata> ->
        tokenService.add(
            chainId = nft.chainId,
            address = nft.address,
            name = token.name,
            symbol = nft.symbol ?: token.symbol,
            decimals = nft.decimals ?: token.decimals,
            icon = token.icon
                ?: icon?.second?.takeIf { icon.first == token.address },
            type = token.type.map(),
            tokenName = nft.name,
            tokenId = nft.tokenId,
            uri = nft.uri,
            image = nft.image,
            external = nft.external,
        ).bind()
    }

    return remember {
        object : CreateNftState {
            override val loading: Boolean get() = create.inProgress
            override val failure: TokenFailure? get() = create.failure
            override fun create(token: TokenMetadata, nft: NftMetadata) {
                create(token to nft)
            }
        }
    }
}

@Stable
interface CreateNftState {
    val loading: Boolean
    val failure: TokenFailure?
    fun create(token: TokenMetadata, nft: NftMetadata)
}
