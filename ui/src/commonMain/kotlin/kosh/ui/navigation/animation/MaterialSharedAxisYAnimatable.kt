package kosh.ui.navigation.animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackEvent

@Composable
fun materialSharedAxisYAnimatable(
    slide: Int = LocalDensity.current.run { 30.dp.roundToPx() },
): (BackEvent) -> PredictiveBackAnimatable = remember(slide) {
    { initialBackEvent ->
        MaterialSharedAxisYAnimatable(
            initialBackEvent = initialBackEvent,
            slide = slide,
        )
    }
}

class MaterialSharedAxisYAnimatable(
    initialBackEvent: BackEvent,
    private val slide: Int,
) : MaterialPredictiveBackAnimatable(initialBackEvent) {

    override val exitModifier: Modifier
        get() = Modifier.graphicsLayer {
            alpha = exitFade.value
            translationY = slide * exitSlide.value
        }

    override val enterModifier: Modifier
        get() = Modifier.graphicsLayer {
            alpha = enterFade.value
            translationY = -slide * enterSlide.value
        }
}

