package kosh.ui.wc

import androidx.compose.runtime.Composable
import kosh.domain.entities.TokenEntity
import kosh.domain.models.ChainAddress
import kosh.domain.models.eip55
import kosh.domain.models.wc.WcRequest
import kosh.presentation.component.textfield.rememberTextField
import kosh.presentation.token.rememberSearchToken
import kosh.presentation.wc.rememberWatchAssetRequest
import kosh.ui.token.SearchTokenScreen

@Composable
fun WcWatchTokenScreen(
    id: WcRequest.Id,
    chainAddress: ChainAddress,
    onNavigateUp: () -> Unit,
    onResult: (TokenEntity.Id) -> Unit,
    onNft: (ChainAddress) -> Unit,
) {
    val watchAsset = rememberWatchAssetRequest(id)

    val query = rememberTextField(chainAddress.address.eip55())

    val icon = watchAsset.call?.address?.let { address ->
        watchAsset.call?.icon?.let { icon ->
            address to icon
        }
    }

    SearchTokenScreen(
        address = chainAddress.address,
        query = query,
        searchToken = rememberSearchToken(query.first.text, icon),
        onResult = {
            watchAsset.onWatch()
            onResult(it)
        },
        onNavigateUp = onNavigateUp,
        onNft = onNft,
    )
}
