package kosh.ui.component.icon

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter

class IconPainter(
    private val icon: Painter,
    private val color: Color,
    private val containerColor: Color,
) : Painter() {

    override val intrinsicSize: Size
        get() = icon.intrinsicSize

    override fun DrawScope.onDraw() {
        drawRect(containerColor)
        with(icon) {
            draw(size, colorFilter = ColorFilter.tint(color))
        }
    }
}
