package kosh.ui.navigation.animation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.essenty.backhandler.BackHandler

@Composable
actual fun <C : Any, T : Any> backStackAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit,
): StackAnimation<C, T> {
    val predictiveAnimatable = materialSharedAxisXAnimatable()
    return predictiveBackAnimation(
        backHandler = backHandler,
        fallbackAnimation = stackAnimationSharedAxisX(),
        selector = { initialBackEvent, _, _ ->
            predictiveAnimatable(initialBackEvent)
        },
        onBack = onBack,
    )
}
