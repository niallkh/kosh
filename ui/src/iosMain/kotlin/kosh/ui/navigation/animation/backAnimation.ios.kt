package kosh.ui.navigation.animation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.essenty.backhandler.BackHandler

@Composable
actual fun <C : Any, T : Any> backAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit,
): StackAnimation<C, T> = predictiveBackAnimation(
    backHandler = backHandler,
    fallbackAnimation = stackAnimation(slideAnimation()),
    selector = { initialBackEvent, _, _ ->
        predictiveBackAnimatable(
            initialBackEvent = initialBackEvent,
            exitModifier = { progress, _ -> Modifier.slideExitModifier(progress = progress) },
            enterModifier = { progress, _ -> Modifier.slideEnterModifier(progress = progress) },
        )
    },
    onBack = onBack,
)

private fun Modifier.slideExitModifier(progress: Float): Modifier =
    offsetXFactor(progress)

private fun Modifier.slideEnterModifier(progress: Float): Modifier =
    fade(progress).offsetXFactor((progress - 1f) * 0.5f)

private fun Modifier.fade(factor: Float) =
    drawWithContent {
        drawContent()
        drawRect(color = Color(red = 0F, green = 0F, blue = 0F, alpha = (1F - factor) / 4F))
    }

private fun Modifier.offsetXFactor(factor: Float): Modifier =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        layout(placeable.width, placeable.height) {
            placeable.placeRelative(x = (placeable.width.toFloat() * factor).toInt(), y = 0)
        }
    }
