package kosh.ui.token

import androidx.core.graphics.drawable.toBitmap
import com.seiko.imageloader.Image
import com.seiko.imageloader.size

fun Image.pixels(): IntArray {
    val bitmap = drawable.toBitmap()

    return IntArray(bitmap.size).also {
        bitmap.getPixels(it, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
    }
}
