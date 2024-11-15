package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.Web3Failure
import kosh.domain.models.token.TokenMetadata
import kosh.domain.serializers.ImmutableList
import kosh.domain.usecases.token.TokenDiscoveryService
import kosh.presentation.core.di
import kosh.presentation.rememberLoad
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun rememberSearchToken(
    query: String,
    tokenDiscoveryService: TokenDiscoveryService = di { domain.tokenDiscoveryService },
): SearchTokenState {
    val tokens = rememberLoad(query) {
        tokenDiscoveryService.searchToken(query).bind().toPersistentList()
    }

    return remember {
        object : SearchTokenState {
            override val tokens: ImmutableList<TokenMetadata>
                get() = tokens.result ?: persistentListOf()
            override val loading: Boolean get() = tokens.loading
            override val failure: Web3Failure? get() = tokens.failure
            override fun retry() {
                tokens.retry()
            }
        }
    }
}

@Stable
interface SearchTokenState {
    val tokens: ImmutableList<TokenMetadata>
    val loading: Boolean
    val failure: Web3Failure?
    fun retry()
}
