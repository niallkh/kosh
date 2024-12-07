package kosh.presentation.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.remember
import com.arkivanov.essenty.lifecycle.Lifecycle
import kosh.presentation.core.LocalPresentationContext
import kosh.presentation.core.PresentationContext
import kosh.presentation.core.getOrCreate

@Composable
fun <T : Any> rememberRetained(
    vararg inputs: Any?,
    key: String = currentCompositeKeyHash.toString(36),
    init: @DisallowComposableCalls () -> T,
): T {
    val presentationContext = LocalPresentationContext.current

    val holder = remember {
        presentationContext.getOrCreate(key) {
            Holder(inputs, init(), presentationContext, key)
        }
    }

    val value = holder.getValueIfInputsDidntChange(inputs) ?: init()

    SideEffect {
        holder.update(inputs, value, presentationContext, key)
    }

    return value
}

private class Holder<T>(
    private var inputs: Array<out Any?>,
    private var value: T,
    private var presentationContext: PresentationContext,
    private var key: String,
) : RememberObserver, Lifecycle.Callbacks {

    fun getValueIfInputsDidntChange(
        inputs: Array<out Any?>,
    ): T? {
        return if (inputs.contentEquals(this.inputs)) {
            value
        } else {
            null
        }
    }

    fun update(
        inputs: Array<out Any?>,
        value: T,
        presentationContext: PresentationContext,
        key: String,
    ) {
        if (presentationContext != this.presentationContext || key != this.key) {
            remove(this.key)
            this.presentationContext = presentationContext
            this.key = key
            put(key)
        }

        this.inputs = inputs
        this.value = value
    }

    private fun put(key: String) {
        presentationContext.container[key] = this
    }

    private fun remove(key: String) {
        presentationContext.container.remove(key)
    }

    override fun onRemembered() {
    }

    override fun onForgotten() {
    }

    override fun onAbandoned() {
    }
}
