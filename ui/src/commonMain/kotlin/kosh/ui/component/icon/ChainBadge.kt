package kosh.ui.component.icon

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kosh.domain.entities.NetworkEntity
import kosh.domain.models.ChainId
import kosh.domain.models.Uri

@Composable
fun ChainBadge(
    modifier: Modifier,
    network: NetworkEntity?,
) {
    if (network != null) {
        ChainBadge(
            modifier = modifier,
            chainId = network.chainId,
            symbol = network.name,
            icon = network.icon
        )
    } else {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = Icons.Default.Error,
            tint = MaterialTheme.colorScheme.error,
            contentDescription = "Network Not Found"
        )
    }
}

@Composable
fun ChainBadge(
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
