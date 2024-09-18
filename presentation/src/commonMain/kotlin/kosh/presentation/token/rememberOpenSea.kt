package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.arbitrumOne
import kosh.domain.models.avalanche
import kosh.domain.models.base
import kosh.domain.models.ethereum
import kosh.domain.models.optimism
import kosh.domain.models.polygonPos
import kosh.domain.serializers.BigInteger

@Composable
fun rememberOpenSea(): OpenSeaState {

    val uriHandler = LocalUriHandler.current

    return OpenSeaState(
        openNft = { chainId, address, tokenId ->
            chainId.map()?.let { chain ->
                uriHandler.openUri("https://opensea.io/assets/$chain/${address}/${tokenId}")
            }
        }
    )
}

data class OpenSeaState(
    val openNft: (ChainId, Address, BigInteger) -> Unit,
)

private fun ChainId.map() = when (this) {
    ethereum -> "ethereum"
    arbitrumOne -> "arbitrum"
    polygonPos -> "matic"
    base -> "base"
    avalanche -> "avalanche"
    optimism -> "optimism"
    else -> null
}
