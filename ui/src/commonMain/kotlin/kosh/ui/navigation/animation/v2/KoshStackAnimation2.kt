@file:OptIn(ExperimentalTransitionApi::class)

package kosh.ui.navigation.animation.v2

import androidx.compose.animation.core.ExperimentalTransitionApi
import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.createChildTransition
import androidx.compose.animation.core.rememberTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.lerp
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.backhandler.BackEvent
import com.arkivanov.essenty.backhandler.BackEvent.SwipeEdge
import com.arkivanov.essenty.backhandler.BackHandler
import kosh.ui.navigation.animation.dropLast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
private fun <C : Any, T : Any> koshStackAnimation2(
    backHandler: BackHandler,
    onBack: () -> Unit,
    animation: (C) -> StackAnimationData2,
): StackAnimation<C, T> {
    return KoshStackAnimation2(
        pushEnter = { animation(it).pushEnter(this) },
        pushExit = { animation(it).pushExit(this) },
        popEnter = { animation(it).popEnter(this) },
        popExit = { animation(it).popExit(this) },
        backEnter = { it1, it2 -> animation(it1).backEnter(this, it2) },
        backExit = { it1, it2 -> animation(it1).backExit(this, it2) },
        backHandler = backHandler,
        backCallback = onBack
    )
}

private class KoshStackAnimation2<C : Any, T : Any>(
    private val pushEnter: @Composable Transition<Boolean>.(C) -> Modifier,
    private val pushExit: @Composable Transition<Boolean>.(C) -> Modifier,
    private val popEnter: @Composable Transition<Boolean>.(C) -> Modifier,
    private val popExit: @Composable Transition<Boolean>.(C) -> Modifier,
    private val backEnter: @Composable Transition<Boolean>.(C, BackTransition) -> Modifier,
    private val backExit: @Composable Transition<Boolean>.(C, BackTransition) -> Modifier,
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
        val transitionHandler = remember {
            TransitionHandler(stack, coroutineScope, backCallback)
        }.apply {
            update(stack)
        }

        val transition = rememberTransition(transitionHandler.backAnimation)

        transition.AnimatedContent2(
            modifier = modifier,
            animation = { stateForContent ->
                val visible = createChildTransition { parentState ->
                    parentState.active == stateForContent.active
                }
                val enter = visible.segment.targetState

                when {
                    currentState == targetState -> {
                        Modifier
                    }

                    transitionHandler.backStarted -> {
                        val configuration = segment.initialState.active.configuration
                        if (enter) visible.backEnter(configuration, transitionHandler)
                        else visible.backExit(configuration, transitionHandler)
                    }

                    isPop(segment.initialState, segment.targetState) -> {
                        val configuration = segment.initialState.active.configuration
                        if (enter) visible.popEnter(configuration)
                        else visible.popExit(configuration)
                    }

                    else -> {
                        val configuration = segment.targetState.active.configuration
                        if (enter) visible.pushEnter(configuration)
                        else visible.pushExit(configuration)
                    }
                }
            },
            contentKey = { it.active.configuration },
        ) {
            Box(Modifier.fillMaxSize()) {
                content(it.active)
            }
        }

        if (stack.backStack.isNotEmpty()) {
            DisposableEffect(backHandler, backCallback) {
                logger.v { "registerCallback()" }
                backHandler.register(transitionHandler)
                onDispose {
                    logger.v { "unregisterCallback()" }
                    backHandler.unregister(transitionHandler)
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

    inner class TransitionHandler(
        initial: ChildStack<C, T>,
        private val coroutineScope: CoroutineScope,
        private val backCallback: () -> Unit,
    ) : BackCallback(), BackTransition {
        private val logger = Logger.withTag("[K]AnimationState")

        val backAnimation = SeekableTransitionState(initial)

        internal var backStarted by mutableStateOf(false)
        private var currentStack: ChildStack<C, T> = initial

        override var touchX by mutableStateOf(0f)
        override var touchY by mutableStateOf(0f)
        override var leftEdge by mutableStateOf(false)
        override var released by mutableStateOf(false)

        fun update(updated: ChildStack<C, T>) {
            logger.v { "update()" }
            if (!backStarted && currentStack != updated) {
                currentStack = updated
                coroutineScope.launch {
                    backAnimation.animateTo(updated)
                }
            }
        }

        override fun onBackStarted(backEvent: BackEvent) {
            logger.v { "onBackStarted()" }
            if (backAnimation.currentState == backAnimation.targetState) {
                coroutineScope.launch {
                    backStarted = true
                    released = false
                    touchX = backEvent.touchX
                    touchY = backEvent.touchY
                    leftEdge = backEvent.swipeEdge == SwipeEdge.LEFT

                    val progress = lerp(0f, 0.3f, backEvent.progress)
                    backAnimation.seekTo(progress, currentStack.dropLast())
                }
            }
        }

        override fun onBackProgressed(backEvent: BackEvent) {
            if (backStarted) {
                coroutineScope.launch {
                    touchX = backEvent.touchX
                    touchY = backEvent.touchY
                    leftEdge = backEvent.swipeEdge == SwipeEdge.LEFT

                    val progress = lerp(0f, 0.3f, backEvent.progress)
                    backAnimation.seekTo(progress, currentStack.dropLast())
                }
            }
        }

        override fun onBackCancelled() {
            logger.v { "onBackCancelled()" }
            if (backStarted) {
                coroutineScope.launch {
                    backAnimation.seekTo(0f, currentStack.dropLast())
                    backAnimation.snapTo(currentStack)
                    backStarted = false
                }
            }
        }

        override fun onBack() {
            logger.v { "onBack()" }
            if (backStarted) {
                coroutineScope.launch {
                    released = true
                    backAnimation.animateTo(currentStack.dropLast())
                    backStarted = false

                    backCallback()
                }
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
            key(contentKey(it)) {
                Box(animation(it)) {
                    content(it)
                }
            }
        }
    }
}

@Stable
interface BackTransition {
    val touchX: Float
    val touchY: Float
    val leftEdge: Boolean
    val released: Boolean
}
