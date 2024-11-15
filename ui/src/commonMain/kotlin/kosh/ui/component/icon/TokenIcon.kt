package kosh.ui.component.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
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
import kosh.domain.models.token.TokenMetadata
import kosh.ui.component.colors.symbolColor
import kosh.ui.component.placeholder.placeholder

@Composable
fun TokenIcon(
    token: TokenEntity?,
    modifier: Modifier = Modifier,
) {
    TokenIcon(
        symbol = token?.symbol ?: "Lorem",
        icon = token?.icon ?: token?.image,
        placeholder = token == null,
        modifier = modifier,
    )
}

@Composable
fun TokenIcon(
    token: TokenMetadata?,
    modifier: Modifier = Modifier,
) {
    TokenIcon(
        symbol = token?.symbol ?: "Lorem",
        icon = token?.icon,
        placeholder = token == null,
        modifier = modifier,
    )
}

@Composable
private fun TokenIcon(
    symbol: String,
    icon: Uri?,
    placeholder: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val request = remember(icon) {
        ImageRequest {
            data(icon)
            options { maxImageSize = 128 }
        }
    }

    val symbolPainter = @Composable {
        val colorScheme = symbolColor(symbol, isSystemInDarkTheme())

        rememberSymbolIconPainter(
            symbol = symbol,
            color = colorScheme().primary,
            containerColor = colorScheme().secondaryContainer,
        )
    }

    Image(
        modifier = modifier
            .clip(CircleShape)
            .placeholder(placeholder),
        painter = rememberImagePainter(
            request = request,
            placeholderPainter = symbolPainter,
            errorPainter = symbolPainter,
        ),
        contentDescription = "TokenIcon",
        contentScale = ContentScale.Crop,
    )
}
