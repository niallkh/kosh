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
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.Uri
import kosh.domain.models.token.TokenMetadata
import kosh.domain.serializers.ImmutableList
import kosh.domain.usecases.token.TokenDiscoveryService
import kosh.presentation.di.di
import kosh.presentation.di.rememberRetainable
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun rememberSearchToken(
    query: String,
    icon: Pair<Address, Uri>? = null,
    tokenDiscoveryService: TokenDiscoveryService = di { domain.tokenDiscoveryService },
): SearchTokenState {
    var tokens by rememberRetainable { mutableStateOf(persistentListOf<TokenMetadata>()) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<Web3Failure?>(null) }
    var retry by remember { mutableIntStateOf(0) }

    LaunchedEffect(retry, query) {
        loading = true

        recover({
            tokens = tokenDiscoveryService.searchToken(query).bind().toPersistentList()

            loading = false
        }) {
            failure = it
            loading = false
        }
    }

    LaunchedEffect(icon, tokens) {
        if (tokens.size == 1
            && tokens.first().address == icon?.first
            && tokens.first().icon != icon.second
        ) {
            tokens = tokens.toPersistentList().mutate {
                it[0] = it[0].copy(icon = icon.second)
            }
        }
    }

    return SearchTokenState(
        tokens = tokens,
        loading = loading,
        failure = failure,
        retry = { retry++ }
    )
}

@Immutable
data class SearchTokenState(
    val tokens: ImmutableList<TokenMetadata>,
    val loading: Boolean,
    val failure: Web3Failure?,
    val retry: () -> Unit,
)
