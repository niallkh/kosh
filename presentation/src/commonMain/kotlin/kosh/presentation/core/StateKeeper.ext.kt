package kosh.presentation.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.essenty.statekeeper.StateKeeper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty

inline fun <reified T : @Serializable Any> StateKeeper.mutableStateFlow(
    serializer: KSerializer<T> = serializer(),
    default: () -> T,
): MutableStateFlow<T> {
    val key = checkNotNull(T::class.qualifiedName)
    val initial = consume(
        key = key,
        strategy = serializer
    ) ?: default()
    val stateFlow = MutableStateFlow(initial)
    register(key, serializer) { stateFlow.value }
    return stateFlow
}

inline fun <reified T : @Serializable Any> StateKeeper.observableState(
    serializer: KSerializer<T>,
    default: () -> T,
): ReadWriteProperty<Any?, T> {
    val key = checkNotNull(T::class.qualifiedName)
    val initial = consume(
        key = key,
        strategy = serializer
    ) ?: default()

    var value = initial

    val observable = Delegates.observable(initial) { _, _, newValue ->
        value = newValue
    }
    register(key, serializer) { value }
    return observable
}

inline fun <reified T : @Serializable Any> StateKeeper.mutableState(
    serializer: KSerializer<T>,
    default: () -> T,
): MutableState<T> {
    val key = checkNotNull(T::class.qualifiedName)
    val initial = consume(
        key = key,
        strategy = serializer
    ) ?: default()

    val state = mutableStateOf(initial)
    register(key, serializer) { state.value }
    return state
}
