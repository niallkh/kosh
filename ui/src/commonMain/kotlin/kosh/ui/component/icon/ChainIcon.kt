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
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.ui.component.colors.chainColor

@Composable
fun ChainIcon(
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
            color = colorScheme.primary,
            containerColor = colorScheme.secondaryContainer,
        )
    }

    Image(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall),
        painter = rememberImagePainter(
            request = request,
            errorPainter = symbolPainter,
            placeholderPainter = symbolPainter,
        ),
        contentDescription = "ChainIcon",
        contentScale = ContentScale.Crop,
    )
}
