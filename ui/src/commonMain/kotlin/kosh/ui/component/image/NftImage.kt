package kosh.ui.component.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImagePainter
import kosh.domain.serializers.Uri

@Composable
fun NftImage(
    modifier: Modifier = Modifier,
    image: Uri,
    maxSize: Int,
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
            .clip(shape),
        painter = rememberImagePainter(request),
        contentDescription = "NFT Image",
        contentScale = ContentScale.Crop,
    )
}
