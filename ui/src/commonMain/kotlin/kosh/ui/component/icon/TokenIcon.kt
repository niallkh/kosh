package kosh.ui.component.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImagePainter
import kosh.domain.entities.TokenEntity
import kosh.domain.models.Uri
import kosh.ui.component.colors.symbolColor
import kosh.ui.component.theme.LocalIsDark

@Composable
fun TokenIcon(
    token: TokenEntity,
    modifier: Modifier = Modifier,
) {
    TokenIcon(
        symbol = token.symbol,
        icon = token.icon,
        modifier = modifier,
    )
}

@Composable
fun TokenIcon(
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
        val colorScheme = symbolColor(symbol, LocalIsDark.current)

        rememberSymbolIconPainter(
            symbol = symbol,
            color = colorScheme.primary,
            containerColor = colorScheme.secondaryContainer,
        )
    }

    Image(
        modifier = modifier.clip(CircleShape),
        painter = rememberImagePainter(
            request = request,
            errorPainter = symbolPainter,
        ),
        contentDescription = "ChainIcon",
        contentScale = ContentScale.Crop,
    )
}
