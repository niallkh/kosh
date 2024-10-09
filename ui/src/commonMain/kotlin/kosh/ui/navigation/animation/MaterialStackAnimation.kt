@file:OptIn(ExperimentalAnimationApi::class)

package kosh.ui.navigation.animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandler

class MaterialStackAnimation<C : Any, T : Any>(
    private val pushTransform: AnimatedContentTransitionScope<ChildStack<C, T>>.(C) -> ContentTransform,
    private val popTransform: AnimatedContentTransitionScope<ChildStack<C, T>>.(C) -> ContentTransform,
) : StackAnimation<C, T> {

    @Composable
    override operator fun invoke(
        stack: ChildStack<C, T>,
        modifier: Modifier,
        content: @Composable (child: Child.Created<C, T>) -> Unit,
    ) {
        AnimatedContent(
            modifier = modifier,
            targetState = stack,
            contentKey = { it.active.configuration },
            transitionSpec = {
                if (isPop(targetState, initialState)) {
                    popTransform(initialState.active.configuration)
                } else {
                    pushTransform(targetState.active.configuration)
                }
            }) {
            Box(Modifier.fillMaxSize()) {
                content(it.active)

                if (transition.isRunning) {
                    Overlay(Modifier.matchParentSize())
                }
            }
        }
    }

    @Composable
    private fun Overlay(modifier: Modifier) {
        Box(modifier = modifier.pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    event.changes.forEach { it.consume() }
                }
            }
        })
    }

    private fun isPop(targetStack: ChildStack<C, T>, currentStack: ChildStack<C, T>): Boolean {
        return targetStack.size < currentStack.size && targetStack.active.configuration in currentStack.backStack
                || currentStack.backStack.isEmpty() && currentStack.active.configuration !in targetStack.backStack
    }

    private val ChildStack<*, *>.size: Int
        inline get() = items.size

    private operator fun <C : Any> Iterable<Child<C, *>>.contains(config: C): Boolean =
        any { it.configuration == config }
}

@Composable
fun <C : Any, T : Any> materialStackAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit,
    animation: Density.(C) -> StackAnimationData,
): StackAnimation<C, T> {
    val density = LocalDensity.current

    val stackAnimation = MaterialStackAnimation<C, T>(
        pushTransform = {
            ContentTransform(
                targetContentEnter = animation(density, it).pushEnter,
                initialContentExit = animation(density, it).pushExit,
                targetContentZIndex = 1f
            )
        },
        popTransform = {
            ContentTransform(
                targetContentEnter = animation(density, it).popEnter,
                initialContentExit = animation(density, it).popExit,
                targetContentZIndex = -1f
            )
        },
    )

    return predictiveBackAnimation(
        backHandler = backHandler,
        fallbackAnimation = stackAnimation,
        selector = { initialBackEvent, exit, _ ->
            animation(density, exit.configuration).predictiveBack(initialBackEvent)
        },
        onBack = onBack,
    )
}
