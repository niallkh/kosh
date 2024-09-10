package kosh.ui.component.icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import kotlin.math.roundToInt

class SymbolIconPainter(
    private val symbol: String,
    private val color: Color,
    private val containerColor: Color,
    private val textMeasurer: TextMeasurer,
    private val textStyle: TextStyle,
) : Painter() {

    override val intrinsicSize: Size = Size(40f, 40f)

    override fun DrawScope.onDraw() {

        drawRoundRect(containerColor)

        val standard = size.toDpSize().width > 16.dp

        val scale = if (standard) 0.9f else 0.5f
        val text = if (standard) symbol.take(5) else symbol.take(3)

        scale(scale) {
            val result = textMeasurer.measure(
                text = text,
                maxLines = 1,
                density = this,
                layoutDirection = layoutDirection,
                style = textStyle,
                constraints = Constraints(
                    maxWidth = (size.width / scale * 0.9).roundToInt(),
                    maxHeight = (size.height / scale * 0.9).roundToInt()
                ),
            )

            drawText(
                textLayoutResult = result,
                topLeft = size.center - result.size.center.toOffset(),
                color = color,
            )
        }
    }
}

@Composable
fun rememberSymbolIconPainter(
    symbol: String,
    containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    color: Color = contentColorFor(containerColor),
): Painter {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = MaterialTheme.typography.labelMedium

    return remember(symbol, textMeasurer, textStyle, containerColor, color) {
        SymbolIconPainter(
            symbol = symbol,
            textMeasurer = textMeasurer,
            textStyle = textStyle,
            containerColor = containerColor,
            color = color,
        )
    }
}
