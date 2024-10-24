package kosh.presentation.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.remember
import kosh.presentation.core.LocalUiContext
import kosh.presentation.core.UiContext
import kosh.presentation.core.getOrCreate

@Composable
fun <T : Any> rememberRetained(
    vararg inputs: Any?,
    key: String = currentCompositeKeyHash.toString(36),
    init: @DisallowComposableCalls () -> T,
): T {
    val uiContext = LocalUiContext.current

    val holder = remember {
        uiContext.getOrCreate(key) {
            Holder(inputs, init(), uiContext, key)
        }
    }

    val value = holder.getValueIfInputsDidntChange(inputs) ?: init()

    SideEffect {
        holder.update(inputs, value, uiContext, key)
    }

    return value
}

private class Holder<T>(
    private var inputs: Array<out Any?>,
    private var value: T,
    private var uiContext: UiContext,
    private var key: String,
) : RememberObserver {

    fun update(
        inputs: Array<out Any?>,
        value: T,
        uiContext: UiContext,
        key: String,
    ) {
        if (uiContext != this.uiContext || key == this.key) {
            remove()
            this.uiContext = uiContext
            this.key = key
            put()
        }

        this.inputs = inputs
        this.value = value
    }

    fun getValueIfInputsDidntChange(
        inputs: Array<out Any?>,
    ): T? {
        return if (inputs.contentEquals(this.inputs)) {
            value
        } else {
            null
        }
    }

    private fun put() {
        uiContext.container[key] = this
    }

    private fun remove() {
        uiContext.container.remove(key)
    }

    override fun onRemembered() {
        put()
    }

    override fun onForgotten() {
    }

    override fun onAbandoned() {
    }
}
