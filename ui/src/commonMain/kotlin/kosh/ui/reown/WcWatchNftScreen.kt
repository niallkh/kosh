package kosh.ui.reown

import androidx.compose.runtime.Composable
import kosh.domain.models.ChainAddress
import kosh.domain.models.reown.WcRequest
import kosh.domain.serializers.BigInteger
import kosh.presentation.component.textfield.rememberTextField
import kosh.presentation.token.rememberSearchNft
import kosh.presentation.wc.rememberWatchAssetRequest
import kosh.ui.token.SearchNftScreen

@Composable
fun WcWatchNftScreen(
    id: WcRequest.Id,
    chainAddress: ChainAddress,
    tokenId: BigInteger?,
    onFinish: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    val watchAsset = rememberWatchAssetRequest(id)

    val icon = watchAsset.call?.address?.let { address ->
        watchAsset.call?.icon?.let { icon ->
            address to icon
        }
    }

    val query = rememberTextField(tokenId?.toString())

    SearchNftScreen(
        chainAddress = chainAddress,
        tokenId = tokenId,
        query = query,
        searchNft = rememberSearchNft(chainAddress, query.first.text, icon),
        onFinish = {
            watchAsset.onWatch()
            onFinish()
        },
        onNavigateUp = onNavigateUp,
    )
}
