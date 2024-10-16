package kosh.ui.navigation.animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.backhandler.BackEvent
import com.arkivanov.essenty.backhandler.BackHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun <C : Any, T : Any> koshStackAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit,
    animation: Density.(C) -> StackAnimationData,
): StackAnimation<C, T> {
    val density = LocalDensity.current
    return KoshStackAnimation(
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
        predictiveTransform = {
            ContentTransform(
                targetContentEnter = animation(density, it).backEnter
                    ?: animation(density, it).popEnter,
                initialContentExit = animation(density, it).backExit
                    ?: animation(density, it).popExit,
                targetContentZIndex = -1f,
            )
        },
        backCallback = onBack,
        backHandler = backHandler,
    )
}

class KoshStackAnimation<C : Any, T : Any>(
    private val pushTransform: AnimatedContentTransitionScope<ChildStack<C, T>>.(C) -> ContentTransform,
    private val popTransform: AnimatedContentTransitionScope<ChildStack<C, T>>.(C) -> ContentTransform,
    private val predictiveTransform: (AnimatedContentTransitionScope<ChildStack<C, T>>.(C) -> ContentTransform)? = null,
    private val backHandler: BackHandler? = null,
    private val backCallback: (() -> Unit)? = null,
) : StackAnimation<C, T> {

    private val logger = Logger.withTag("[K]KoshStackAnimation")

    @Composable
    override operator fun invoke(
        stack: ChildStack<C, T>,
        modifier: Modifier,
        content: @Composable (child: Child.Created<C, T>) -> Unit,
    ) {
        val coroutineScope = rememberCoroutineScope()
        val animationState = remember {
            AnimationState(stack, coroutineScope, backCallback)
        }.apply {
            update(stack)
        }

        val transition = rememberTransition(animationState.animation)

        transition.AnimatedContent(
            modifier = modifier,
            contentKey = { it.active.configuration },
            transitionSpec = {
                if (isPop(initialState, targetState)) {
                    if (animationState.predictiveAnimationStarted) {
                        (predictiveTransform ?: popTransform)(initialState.active.configuration)
                    } else {
                        popTransform(initialState.active.configuration)
                    }
                } else {
                    pushTransform(targetState.active.configuration)
                }
            }
        ) {

            Box(Modifier.fillMaxSize()) {
                content(it.active)
            }
        }

        if (stack.backStack.isNotEmpty() && backHandler != null) {
            DisposableEffect(backHandler, backCallback) {
                backHandler.register(animationState)
                onDispose {
                    backHandler.unregister(animationState)
                }
            }
        }
    }

    private fun isPop(initial: ChildStack<C, T>, target: ChildStack<C, T>): Boolean {
        return target.size < initial.size && target.active.configuration in initial.backStack
                || initial.backStack.isEmpty() && initial.active.configuration !in target.backStack
    }

    private val ChildStack<*, *>.size: Int
        inline get() = items.size

    private operator fun <C : Any> Iterable<Child<C, *>>.contains(config: C): Boolean =
        any { it.configuration == config }

    inner class AnimationState(
        initial: ChildStack<C, T>,
        private val coroutineScope: CoroutineScope,
        private val backCallback: (() -> Unit)?,
    ) : BackCallback() {
        private val logger = Logger.withTag("[K]AnimationState")

        val animation = SeekableTransitionState(initial)
        var predictiveAnimationStarted = false
        private var currentStack: ChildStack<C, T> = initial

        fun update(updated: ChildStack<C, T>) {
            if (updated == currentStack) return
            logger.v { "update()" }
            currentStack = updated
            coroutineScope.launch {
                animation.animateTo(updated)
            }
        }

        override fun onBackStarted(backEvent: BackEvent) {
            logger.v { "onBackStarted()" }
            coroutineScope.launch {
                predictiveAnimationStarted = true
                animation.seekTo(backEvent.progress, currentStack.dropLast())
            }
        }

        override fun onBackProgressed(backEvent: BackEvent) {
            coroutineScope.launch {
                animation.seekTo(backEvent.progress)
            }
        }

        override fun onBackCancelled() {
            logger.v { "onBackCancelled()" }
            coroutineScope.launch {
                try {
                    animation.animateTo(currentStack)
                } finally {
                    predictiveAnimationStarted = false
                }
            }
        }

        override fun onBack() {
            logger.v { "onBack()" }
            coroutineScope.launch {
                try {
                    animation.animateTo()
                } finally {
                    predictiveAnimationStarted = false
                }

                backCallback?.invoke()
            }
        }
    }
}

internal fun <C : Any, T : Any> ChildStack<C, T>.dropLast(): ChildStack<C, T> =
    ChildStack(active = backStack.last(), backStack = backStack.dropLast(1))
