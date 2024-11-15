package kosh.ui.component.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImagePainter
import kosh.domain.entities.NetworkEntity
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.models.zeroChain
import kosh.ui.component.colors.chainColor
import kosh.ui.component.placeholder.placeholder

@Composable
fun ChainIcon(
    network: NetworkEntity?,
    modifier: Modifier = Modifier,
) {
    ChainIcon(
        modifier = modifier
            .placeholder(visible = network == null)
            .clip(MaterialTheme.shapes.small),
        chainId = network?.chainId ?: zeroChain,
        symbol = network?.name ?: "Lorem",
        icon = network?.icon
    )
}

@Composable
internal fun ChainIcon(
    chainId: ChainId,
    symbol: String,
    icon: Uri?,
    modifier: Modifier = Modifier,
) {
    val request = remember(icon) {
        ImageRequest {
            data(icon)
            options { maxImageSize = 128 }
        }
    }

    val symbolPainter = @Composable {
        val colorScheme = chainColor(chainId, isSystemInDarkTheme())

        rememberSymbolIconPainter(
            symbol = symbol.substringBefore(" ").uppercase(),
            color = colorScheme().primary,
            containerColor = colorScheme().secondaryContainer,
        )
    }

    Image(
        modifier = modifier,
        painter = rememberImagePainter(
            request = request,
            errorPainter = symbolPainter,
            placeholderPainter = symbolPainter,
        ),
        contentDescription = "ChainIcon",
        contentScale = ContentScale.Crop,
    )
}
