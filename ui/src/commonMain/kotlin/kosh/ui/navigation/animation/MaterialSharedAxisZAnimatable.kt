package kosh.ui.navigation.animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackEvent

@Composable
fun materialSharedAxisZAnimatable(): (BackEvent) -> PredictiveBackAnimatable {
    return remember {
        { initialBackEvent ->
            MaterialSharedAxisZAnimatable(
                initialBackEvent = initialBackEvent,
            )
        }
    }
}

class MaterialSharedAxisZAnimatable(
    initialBackEvent: BackEvent,
) : MaterialPredictiveBackAnimatable(initialBackEvent) {

    override val exitModifier: Modifier
        get() = Modifier.graphicsLayer {
            alpha = exitFade.value
            scaleX = lerp(1f, 0.8f, exitScale.value)
            scaleY = lerp(1f, 0.8f, exitScale.value)
        }

    override val enterModifier: Modifier
        get() = Modifier.graphicsLayer {
            alpha = enterFade.value
            scaleX = lerp(1.1f, 1f, enterScale.value)
            scaleY = lerp(1.1f, 1f, enterScale.value)
        }
}

