package kosh.presentation.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.remember
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kosh.presentation.core.LocalUiContext

@Composable
fun <T> rememberRetainable(
    vararg inputs: Any?,
    key: String = currentCompositeKeyHash.toString(36),
    init: @DisallowComposableCalls () -> T,
): T {
    val instanceKeeper = LocalUiContext.current.instanceKeeper

    val holder = remember {
        instanceKeeper.getOrCreate(key) {
            InstanceHolder(inputs, init(), instanceKeeper, key)
        }
    }

    val value = holder.getValueIfInputsDidntChange(inputs) ?: init()

    SideEffect {
        holder.update(inputs, value, instanceKeeper, key)
    }

    return value
}

private class InstanceHolder<T>(
    private var inputs: Array<out Any?>,
    private var value: T,
    private var instanceKeeper: InstanceKeeper,
    private var key: String,
) : InstanceKeeper.Instance, RememberObserver {

    fun update(
        inputs: Array<out Any?>,
        value: T,
        instanceKeeper: InstanceKeeper,
        key: String,
    ) {
        if (instanceKeeper != this.instanceKeeper || key == this.key) {
            remove()
            this.instanceKeeper = instanceKeeper
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
        remove()
        instanceKeeper.put(key, this)
    }

    private fun remove() {
        if (instanceKeeper.get(key) != null) {
            instanceKeeper.remove(key)
        }
    }

    override fun onRemembered() {
        put()
    }

    override fun onForgotten() { // TODO
    }

    override fun onAbandoned() { // TODO
    }
}
