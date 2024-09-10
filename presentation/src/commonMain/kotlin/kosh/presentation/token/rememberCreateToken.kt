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
import kosh.domain.models.token.TokenMetadata
import kosh.domain.usecases.token.TokenService
import kosh.presentation.di.di

@Composable
fun rememberCreateToken(
    tokenService: TokenService = di { domain.tokenService },
): CreateTokenState {
    var token by remember { mutableStateOf<TokenMetadata?>(null) }
    var created by remember { mutableStateOf<TokenEntity.Id?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<TokenFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }

    LaunchedEffect(retry, token) {
        loading = true

        recover({
            val localToken = token ?: raise(null)

            created = tokenService.add(
                chainId = localToken.chainId,
                address = localToken.address,
                name = localToken.name,
                symbol = localToken.symbol,
                decimals = localToken.decimals,
                icon = localToken.icon,
                type = TokenEntity.Type.Erc20,
                tokenName = null,
                tokenId = null,
                uri = null,
                image = null,
                external = null,
            ).bind()

            failure = null
            loading = false
        }) {
            created = null

            failure = it
            loading = false
        }
    }

    return CreateTokenState(
        created = created,
        loading = loading,
        failure = failure,
        create = {
            require(it.type == TokenMetadata.Type.ERC20)
            retry++
            token = it
        },
        retry = { retry++ }
    )

}

data class CreateTokenState(
    val created: TokenEntity.Id?,
    val loading: Boolean,
    val failure: TokenFailure?,
    val create: (TokenMetadata) -> Unit,
    val retry: () -> Unit,
)
