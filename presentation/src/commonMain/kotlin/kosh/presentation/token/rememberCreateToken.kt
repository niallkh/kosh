package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.entities.TokenEntity
import kosh.domain.failure.TokenFailure
import kosh.domain.models.Address
import kosh.domain.models.Uri
import kosh.domain.models.token.TokenMetadata
import kosh.domain.usecases.token.TokenService
import kosh.presentation.core.di
import kosh.presentation.rememberEitherEffect

@Composable
fun rememberCreateToken(
    icon: Pair<Address, Uri>? = null,
    onCreated: (TokenEntity.Id) -> Unit,
    tokenService: TokenService = di { domain.tokenService },
): CreateTokenState {
    val create = rememberEitherEffect(
        icon,
        onFinish = onCreated
    ) { token: TokenMetadata ->
        tokenService.add(
            chainId = token.chainId,
            address = token.address,
            name = token.name,
            symbol = token.symbol,
            decimals = token.decimals,
            icon = token.icon
                ?: icon?.second?.takeIf { icon.first == token.address },
            type = TokenEntity.Type.Erc20,
            tokenName = null,
            tokenId = null,
            uri = null,
            image = null,
            external = null,
        ).bind()
    }

    return remember {
        object : CreateTokenState {
            override val created: TokenEntity.Id? get() = create.result
            override val loading: Boolean get() = create.inProgress
            override val failure: TokenFailure? get() = create.failure
            override fun create(token: TokenMetadata) {
                create(token)
            }
        }
    }
}

@Stable
interface CreateTokenState {
    val created: TokenEntity.Id?
    val loading: Boolean
    val failure: TokenFailure?
    fun create(token: TokenMetadata)
}
