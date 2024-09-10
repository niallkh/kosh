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
import kosh.domain.models.token.NftExtendedMetadata
import kosh.domain.serializers.Uri
import kosh.domain.usecases.token.TokenDiscoveryService
import kosh.presentation.di.di
import kosh.presentation.di.rememberSerializable

@Composable
fun rememberNftMetadata(
    uri: Uri,
    tokenDiscoveryService: TokenDiscoveryService = di { domain.tokenDiscoveryService },
): NftMetadataState {
    var nft by rememberSerializable { mutableStateOf<NftExtendedMetadata?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<Web3Failure?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var refresh by remember { mutableStateOf(false) }

    LaunchedEffect(uri, retry) {
        loading = true

        recover({
            nft = tokenDiscoveryService.getNftExtendedMetadata(uri, refresh).bind()

            refresh = false
            loading = false
            failure = null
        }) {
            loading = false
            failure = it
        }
    }

    return NftMetadataState(
        nft = nft,
        loading = loading,
        failure = failure,
        retry = { retry++ },
        refresh = {
            retry++
            refresh = true
        }
    )
}

@Immutable
data class NftMetadataState(
    val nft: NftExtendedMetadata?,
    val loading: Boolean,
    val failure: Web3Failure?,
    val retry: () -> Unit,
    val refresh: () -> Unit,
)
