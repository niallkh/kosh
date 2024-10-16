@file:OptIn(ExperimentalTransitionApi::class)

package kosh.ui.navigation.animation

import androidx.compose.animation.core.ExperimentalTransitionApi
import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.createChildTransition
import androidx.compose.animation.core.rememberTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.fastForEach
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
fun <C : Any, T : Any> koshStackAnimation2(
    backHandler: BackHandler,
    onBack: () -> Unit,
    animation: (C) -> StackAnimationData2,
): StackAnimation<C, T> {
    return KoshStackAnimation2(
        pushEnter = { animation(it).pushEnter(this) },
        pushExit = { animation(it).pushExit(this) },
        popEnter = { animation(it).popEnter(this) },
        popExit = { animation(it).popExit(this) },
        backEnter = { animation(it).backEnter(this) },
        backExit = { animation(it).backExit(this) },
        backHandler = backHandler,
        backCallback = onBack
    )
}

class KoshStackAnimation2<C : Any, T : Any>(
    private val pushEnter: @Composable Transition<Boolean>.(C) -> Modifier,
    private val pushExit: @Composable Transition<Boolean>.(C) -> Modifier,
    private val popEnter: @Composable Transition<Boolean>.(C) -> Modifier,
    private val popExit: @Composable Transition<Boolean>.(C) -> Modifier,
    private val backEnter: @Composable Transition<Boolean>.(C) -> Modifier,
    private val backExit: @Composable Transition<Boolean>.(C) -> Modifier,
    private val backHandler: BackHandler,
    private val backCallback: () -> Unit,
) : StackAnimation<C, T> {

    private val logger = Logger.withTag("[K]StackAnimation")

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

        transition.AnimatedContent2(animation = { stateForContent ->
            val visible = createChildTransition { parentState -> parentState == stateForContent }
            val enter = stateForContent == segment.targetState
            if (isPop(segment.initialState, segment.targetState)) {
                val configuration = segment.initialState.active.configuration
                if (animationState.predictiveAnimationStarted) {
                    if (enter) visible.backEnter(configuration)
                    else visible.backExit(configuration)
                } else {
                    if (enter) visible.popEnter(configuration)
                    else visible.popExit(configuration)
                }
            } else {
                val configuration = segment.targetState.active.configuration
                if (enter) visible.pushEnter(configuration)
                else visible.pushExit(configuration)
            }
        }) {
            Box(Modifier.fillMaxSize()) {
                content(it.active)
            }
        }

        if (stack.backStack.isNotEmpty()) {
            DisposableEffect(backHandler, backCallback) {
                logger.v { "registerCallback()" }
                backHandler.register(animationState)
                onDispose {
                    logger.v { "unregisterCallback()" }
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
        private val backCallback: () -> Unit,
    ) : BackCallback() {
        private val logger = Logger.withTag("[K]AnimationState")

        val animation = SeekableTransitionState(initial)
        var predictiveAnimationStarted by mutableStateOf(false)
        private var currentStack: ChildStack<C, T> = initial

        fun update(updated: ChildStack<C, T>) {
            if (!predictiveAnimationStarted) {
                logger.v { "update(updated=${updated})" }
                currentStack = updated
                coroutineScope.launch {
                    animation.animateTo(updated)
                }
            }
        }

        override fun onBackStarted(backEvent: BackEvent) {
            logger.v { "onBackStarted(progress=${backEvent.progress}, ${currentStack.dropLast()})" }
            if (animation.currentState == animation.targetState) {
                coroutineScope.launch {
                    predictiveAnimationStarted = true
                    animation.seekTo(backEvent.progress, currentStack.dropLast())
                }
            }
        }

        override fun onBackProgressed(backEvent: BackEvent) {
            logger.v { "onBackProgressed(progress=${backEvent.progress}, ${animation.targetState})" }
            if (predictiveAnimationStarted) {
                coroutineScope.launch {
                    animation.seekTo(backEvent.progress, currentStack.dropLast())
                }
            }
        }

        override fun onBackCancelled() {
            logger.v { "onBackCancelled(), $currentStack" }
            if (predictiveAnimationStarted) {
                coroutineScope.launch {
                    animation.animateTo(currentStack)
                    predictiveAnimationStarted = false
                }
            }
        }

        override fun onBack() {
            logger.v { "onBack()" }
            if (predictiveAnimationStarted) {
                coroutineScope.launch {
                    animation.animateTo(currentStack.dropLast())
                    predictiveAnimationStarted = false
                }
                backCallback()
            }
        }
    }
}

@Composable
private fun <T> Transition<T>.AnimatedContent2(
    modifier: Modifier = Modifier,
    animation: @Composable Transition<T>.(T) -> Modifier = { Modifier },
    contentKey: (targetState: T) -> Any? = { it },
    content: @Composable (targetState: T) -> Unit,
) {
    Box(modifier) {
        listOfNotNull(
            currentState,
            targetState.takeUnless { contentKey(it) == contentKey(currentState) },
        ).fastForEach {
            key(contentKey(it).toString() + " HERE") {
                Box(animation(it)) {
                    content(it)
                }
            }
        }
    }
}
