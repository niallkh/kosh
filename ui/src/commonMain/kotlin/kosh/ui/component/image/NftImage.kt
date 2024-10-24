package kosh.ui.component.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImagePainter
import kosh.domain.models.Uri
import kosh.ui.component.placeholder.placeholder
import kosh.ui.resources.illustrations.NftEmpty

@Composable
fun NftImage(
    image: Uri?,
    modifier: Modifier = Modifier,
    maxSize: Int = 1024,
    shape: Shape = MaterialTheme.shapes.medium,
) {
    val request = remember(image) {
        ImageRequest {
            data(image)
            options { maxImageSize = maxSize }
        }
    }

    Image(
        modifier = modifier
            .aspectRatio(1f)
            .clip(shape)
            .placeholder(image == null),
        painter = rememberImagePainter(
            request,
            errorPainter = { rememberVectorPainter(NftEmpty()) },
            placeholderPainter = { rememberVectorPainter(NftEmpty()) },
        ),
        contentDescription = "NFT Image",
        contentScale = ContentScale.Crop,
    )
}
