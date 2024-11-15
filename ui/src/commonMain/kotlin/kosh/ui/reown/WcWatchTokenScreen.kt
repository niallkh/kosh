package kosh.ui.reown

import androidx.compose.runtime.Composable
import kosh.domain.models.ChainAddress
import kosh.domain.models.eip55
import kosh.domain.models.reown.WcRequest
import kosh.presentation.component.textfield.rememberTextField
import kosh.presentation.token.rememberCreateToken
import kosh.presentation.token.rememberSearchToken
import kosh.presentation.wc.WatchAssetRequestState
import kosh.presentation.wc.rememberWatchAssetRequest
import kosh.ui.token.SearchTokenScreen

@Composable
fun WcWatchTokenScreen(
    id: WcRequest.Id,
    chainAddress: ChainAddress,
    onNavigateUp: () -> Unit,
    onFinish: () -> Unit,
    onNft: (ChainAddress) -> Unit,
    watchAsset: WatchAssetRequestState = rememberWatchAssetRequest(id),
) {
    val query = rememberTextField(chainAddress.address.eip55())

    val icon = watchAsset.call?.address?.let { address ->
        watchAsset.call?.icon?.let { icon ->
            address to icon
        }
    }

    val createToken = rememberCreateToken(icon, onCreated = {
        watchAsset.onWatch()
        onFinish()
    })

    SearchTokenScreen(
        address = chainAddress.address,
        query = query,
        searchToken = rememberSearchToken(query.value.text),
        onFinish = {
            watchAsset.onWatch()
            onFinish()
        },
        onNavigateUp = onNavigateUp,
        createToken = createToken,
        onNft = onNft,
    )
}
