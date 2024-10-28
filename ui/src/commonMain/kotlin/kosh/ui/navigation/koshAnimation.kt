package kosh.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import co.touchlab.kermit.Logger
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.backhandler.BackEvent
import com.arkivanov.essenty.backhandler.BackHandler
import kosh.ui.navigation.animation.transitions.fadeThroughIn
import kosh.ui.navigation.animation.transitions.fadeThroughOut
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun <T> koshAnimation(
    state: T,
    contentKey: (T) -> Any,
    transitionSpec: AnimatedContentTransitionScope<T>.() -> ContentTransform = {
        fadeThroughIn() togetherWith fadeThroughOut()
    },
    backTransform: (T) -> T = { it },
    canBack: (T) -> Boolean = { false },
    backCallback: (() -> Unit)? = null,
    backHandler: BackHandler? = null,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val animationState = remember {
        AnimationState(state, backTransform, coroutineScope, backCallback)
    }.apply {
        update(state)
    }

    val transition = rememberTransition(animationState.animation)

    transition.AnimatedContent(
        modifier = modifier,
        contentKey = contentKey,
        transitionSpec = transitionSpec
    ) {
        Box(Modifier.fillMaxSize()) {
            content(it)
        }
    }

    if (canBack(state) && backHandler != null) {
        DisposableEffect(backHandler, backCallback) {
            backHandler.register(animationState)
            onDispose {
                backHandler.unregister(animationState)
            }
        }
    }
}

private class AnimationState<T>(
    initial: T,
    private val backTransform: (T) -> T,
    private val coroutineScope: CoroutineScope,
    private val backCallback: (() -> Unit)?,
) : BackCallback() {
    private val logger = Logger.withTag("[K]AnimationState")

    val animation = SeekableTransitionState(initial)
    var predictiveAnimationStarted = false
    private var current: T = initial

    fun update(updated: T) {
        if (updated == current) return
        logger.v { "update()" }
        current = updated
        coroutineScope.launch {
            animation.animateTo(updated)
        }
    }

    override fun onBackStarted(backEvent: BackEvent) {
        logger.v { "onBackStarted()" }
        coroutineScope.launch {
            predictiveAnimationStarted = true
            animation.seekTo(backEvent.progress, backTransform(current))
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
                animation.animateTo(current)
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
