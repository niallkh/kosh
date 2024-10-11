package kosh.presentation.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotMutableState
import androidx.compose.runtime.structuralEqualityPolicy
import com.arkivanov.essenty.statekeeper.StateKeeper
import kosh.presentation.core.LocalUiContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer
import kotlin.reflect.typeOf

@Composable
inline fun <reified T> rememberSerializable(
    vararg inputs: Any?,
    key: String = currentCompositeKeyHash.toString(36),
    noinline init: @DisallowComposableCalls () -> MutableState<T>,
): MutableState<T> = rememberSerializable(
    *inputs,
    stateSerializer = remember(typeOf<T>()) { serializer() },
    key = key,
    init = init,
)

@Composable
fun <T> rememberSerializable(
    vararg inputs: Any?,
    stateSerializer: KSerializer<T>,
    key: String = currentCompositeKeyHash.toString(36),
    init: @DisallowComposableCalls () -> MutableState<T>,
): MutableState<T> = rememberSerializable(
    *inputs,
    serializer = remember(stateSerializer) { StateSerializer(stateSerializer) },
    key = key,
    init = init,
)

@Composable
inline fun <reified T : Any> rememberSerializable(
    vararg inputs: Any?,
    key: String = currentCompositeKeyHash.toString(36),
    noinline init: @DisallowComposableCalls () -> T,
): T = rememberSerializable(
    *inputs,
    serializer = remember(typeOf<T>()) { serializer() },
    key = key,
    init = init,
)

@Composable
fun <T : Any> rememberSerializable(
    vararg inputs: Any?,
    serializer: KSerializer<T>,
    key: String = currentCompositeKeyHash.toString(36),
    init: @DisallowComposableCalls () -> T,
): T {
    val stateKeeper = LocalUiContext.current.stateKeeper

    val holder = remember {
        val restored = stateKeeper.consume(key, serializer) ?: init()
        StateHolder(inputs, restored, key, stateKeeper, serializer)
    }

    val value = holder.getValueIfInputsDidntChange(inputs) ?: init()

    SideEffect {
        holder.update(inputs, value, key, stateKeeper, serializer)
    }

    return value
}

class StateHolder<T : Any>(
    private var inputs: Array<out Any?>,
    private var value: T,
    private var key: String,
    private var stateKeeper: StateKeeper,
    private var serializer: KSerializer<T>,
) : RememberObserver {

    fun update(
        inputs: Array<out Any?>,
        value: T,
        key: String,
        stateKeeper: StateKeeper,
        serializer: KSerializer<T>,
    ) {
        if (key != this.key
            || stateKeeper != this.stateKeeper
            || serializer != this.serializer
        ) {
            unregister()
            this.stateKeeper = stateKeeper
            this.key = key
            this.serializer = serializer
            register()
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

    private fun register() {
        unregister()
        stateKeeper.register(key, serializer) { value }
    }

    private fun unregister() {
        if (stateKeeper.isRegistered(key)) {
            stateKeeper.unregister(key)
        }
    }

    override fun onRemembered() {
        register()
    }

    override fun onAbandoned() {
        unregister()
    }

    override fun onForgotten() {
        unregister()
    }
}

class StateSerializer<T>(serializer: KSerializer<T>) : KSerializer<MutableState<T>> {
    private val holderSerializer = Holder.serializer(serializer)

    override val descriptor: SerialDescriptor
        get() = holderSerializer.descriptor

    override fun deserialize(decoder: Decoder): MutableState<T> {
        val holder = holderSerializer.deserialize(decoder)
        return mutableStateOf(
            holder.value,
            when (holder.policy) {
                Holder.Policy.Structural -> structuralEqualityPolicy()
                Holder.Policy.Referential -> referentialEqualityPolicy()
                Holder.Policy.Never -> neverEqualPolicy()
            }
        )
    }

    override fun serialize(encoder: Encoder, value: MutableState<T>) {
        holderSerializer.serialize(
            encoder,
            Holder(
                value = value.value,
                policy = when ((value as SnapshotMutableState<T>).policy) {
                    structuralEqualityPolicy<T>() -> Holder.Policy.Structural
                    referentialEqualityPolicy<T>() -> Holder.Policy.Referential
                    neverEqualPolicy<T>() -> Holder.Policy.Never
                    else -> error("Invalid policy")
                }
            )
        )
    }

    @Serializable
    data class Holder<T>(
        val value: T,
        val policy: Policy,
    ) {
        enum class Policy {
            Structural,
            Referential,
            Never,
        }
    }
}
