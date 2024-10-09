package kosh.ui.navigation.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackEvent
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

abstract class MaterialPredictiveBackAnimatable(
    initialBackEvent: BackEvent,
) : PredictiveBackAnimatable {

    private val logger = Logger.withTag("[K]MaterialPredictiveBackAnimatable")

    private var lastProgress = 0f

    private val mutex = MutatorMutex()

    protected val exitSlide = Animatable(initialValue = progress(initialBackEvent.progress))
    protected val exitScale = Animatable(initialValue = progress(initialBackEvent.progress))
    protected val exitFade = Animatable(initialValue = 1f)

    protected val enterSlide = Animatable(initialValue = 1f)
    protected val enterScale = Animatable(initialValue = 0f)
    protected val enterFade = Animatable(initialValue = 0f)

    protected open val fadeInSpec = tween<Float>(
        delayMillis = 90,
        durationMillis = 210,
        easing = LinearOutSlowInEasing
    )

    protected open val fadeOutSpec = tween<Float>(
        delayMillis = 0,
        durationMillis = 90,
        easing = FastOutLinearInEasing
    )

    protected open val slideSpec = tween<Float>(
        delayMillis = 0,
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )

    protected open val scaleSpec = tween<Float>(
        delayMillis = 0,
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )

    private suspend fun moveTo(
        state: State,
        priority: MutatePriority = MutatePriority.Default,
    ) {
        logger.v { "moveTo($state)" }
        mutex.mutate(priority = priority) {
            when (state) {
                is State.Preview -> previewState(state.progress)
                State.PreCommit -> preCommitState()
                State.Cancel -> cancelState()
                State.Commit -> commitState()
            }
        }
    }

    private suspend fun previewState(progress: Float) = coroutineScope {
        launch { exitSlide.animateTo(progress) }
        launch { exitScale.animateTo(progress) }
        launch { exitFade.animateTo(1f) }
        launch { enterSlide.animateTo(1f) }
        launch { enterFade.animateTo(0f) }
        launch { enterScale.animateTo(0f) }
    }

    private suspend fun preCommitState() = coroutineScope {
        launch { exitSlide.animateTo(1f, slideSpec) }
        launch { exitScale.animateTo(1f, scaleSpec) }
        launch { exitFade.animateTo(0f, fadeOutSpec) }
        launch { enterSlide.animateTo(0f, slideSpec) }
        launch { enterFade.animateTo(1f, fadeInSpec) }
        launch { enterScale.animateTo(0f, scaleSpec) }
    }

    private suspend fun cancelState() = coroutineScope {
        launch { exitSlide.animateTo(0f, slideSpec) }
        launch { exitScale.animateTo(0f, scaleSpec) }
        launch { exitFade.animateTo(1f, fadeInSpec) }
        launch { enterSlide.animateTo(1f, slideSpec) }
        launch { enterFade.animateTo(0f, fadeOutSpec) }
        launch { enterScale.animateTo(0f, scaleSpec) }
    }

    private suspend fun commitState() = coroutineScope {
        launch { exitSlide.animateTo(1f, slideSpec) }
        launch { exitScale.animateTo(1f, scaleSpec) }
        launch { exitFade.animateTo(0f, fadeOutSpec) }
        launch { enterSlide.animateTo(0f, slideSpec) }
        launch { enterFade.animateTo(1f, fadeInSpec) }
        launch { enterScale.animateTo(1f, scaleSpec) }
    }

    override suspend fun animate(event: BackEvent) {
        val progress = progress(event.progress)
        if (progress == lastProgress) return
        lastProgress = progress

        if (progress < 1f) {
            moveTo(State.Preview(progress))
        } else {
            moveTo(State.PreCommit)
        }
    }

    override suspend fun finish() {
        moveTo(State.Commit)
    }

    override suspend fun cancel() {
        moveTo(State.Cancel)
    }

    private fun progress(progress: Float): Float {
        return progress.coerceIn(0f, COMMIT_THRESHOLD) / COMMIT_THRESHOLD
    }

    private sealed class State {
        data class Preview(val progress: Float) : State()
        data object PreCommit : State()
        data object Cancel : State()
        data object Commit : State()
    }

    companion object {
        private const val COMMIT_THRESHOLD = 0.1f
    }
}
