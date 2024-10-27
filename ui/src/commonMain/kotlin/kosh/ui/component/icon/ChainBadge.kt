package kosh.ui.component.icon

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kosh.domain.entities.NetworkEntity
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.models.zeroChain
import kosh.ui.component.placeholder.placeholder

@Composable
fun ChainBadge(
    modifier: Modifier,
    network: NetworkEntity?,
) {
    ChainBadge(
        chainId = network?.chainId ?: zeroChain,
        symbol = network?.name ?: "",
        icon = network?.icon,
        modifier = modifier
            .placeholder(network == null),
    )
}

@Composable
private fun ChainBadge(
    chainId: ChainId,
    symbol: String,
    icon: Uri?,
    modifier: Modifier = Modifier,
) {
    ChainIcon(
        modifier = modifier
            .clip(RoundedCornerShape(3.dp))
            .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(3.dp))
            .size(16.dp),
        chainId = chainId,
        icon = icon,
        symbol = symbol
    )
}
