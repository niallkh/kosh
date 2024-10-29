package kosh.presentation.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.serializer


@Composable
inline fun <reified T> rememberSerializable(
    stateSerializer: KSerializer<T> = serializer<T>(),
    key: String? = null,
    noinline init: () -> MutableState<T>,
): MutableState<T> = rememberSaveable(
    Unit,
    stateSaver = remember(stateSerializer) { SerializableSaver(stateSerializer) },
    key = key,
    init = init
)

@Composable
inline fun <reified T> rememberSerializable(
    vararg inputs: Any?,
    stateSerializer: KSerializer<T> = serializer(),
    key: String? = null,
    noinline init: () -> MutableState<T>,
): MutableState<T> = rememberSaveable(
    inputs = inputs,
    stateSaver = remember(stateSerializer) { SerializableSaver(stateSerializer) },
    key = key,
    init = init
)

@Composable
inline fun <reified T : Any> rememberSerializable(
    serializer: KSerializer<T> = serializer(),
    key: String? = null,
    noinline init: () -> T,
): T = rememberSaveable(
    Unit,
    saver = remember(serializer) { SerializableSaver(serializer) },
    key = key,
    init = init,
)

@Composable
inline fun <reified T : Any> rememberSerializable(
    vararg inputs: Any?,
    serializer: KSerializer<T> = serializer(),
    key: String? = null,
    noinline init: () -> T,
): T = rememberSaveable(
    inputs = inputs,
    saver = remember(serializer) { SerializableSaver(serializer) },
    key = key,
    init = init,
)

class SerializableSaver<T>(private val serializer: KSerializer<T>) : Saver<T, ByteArray> {
    override fun SaverScope.save(value: T): ByteArray = Cbor.encodeToByteArray(serializer, value)
    override fun restore(value: ByteArray): T = Cbor.decodeFromByteArray(serializer, value)
}
