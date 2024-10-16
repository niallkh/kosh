package kosh.ui.navigation.animation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.essenty.backhandler.BackHandler

@Composable
actual fun <C : Any, T : Any> backStackAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit,
): StackAnimation<C, T> = koshStackAnimation(
    backHandler = backHandler,
    onBack = onBack,
) {
    sharedAxisX()
}
