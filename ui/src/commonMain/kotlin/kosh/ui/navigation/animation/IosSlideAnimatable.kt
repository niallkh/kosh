package kosh.ui.navigation.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackEvent

class IosSlideAnimatable(
    initialBackEvent: BackEvent,
) : PredictiveBackAnimatable {

    private val progress = Animatable(initialValue = initialBackEvent.progress)

    override val exitModifier: Modifier
        get() = Modifier
            .graphicsLayer {
                translationX = lerp(0f, size.width, progress.value)
            }

    override val enterModifier: Modifier
        get() = Modifier
            .graphicsLayer {
                translationX = lerp(-size.width / 2f, 0f, progress.value)
            }
            .drawWithContent {
                drawContent()
                drawRect(
                    color = Color.Black.copy(alpha = lerp(0.25f, 0f, progress.value))
                )
            }

    override suspend fun animate(event: BackEvent) {
        progress.animateTo(targetValue = event.progress)
    }

    override suspend fun finish() {
        progress.animateTo(targetValue = 1F, tween())
    }

    override suspend fun cancel() {
        progress.animateTo(targetValue = 0F, tween())
    }
}

