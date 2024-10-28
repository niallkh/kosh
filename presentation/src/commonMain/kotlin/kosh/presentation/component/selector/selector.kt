package kosh.presentation.component.selector

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kosh.presentation.di.rememberSerializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

@Composable
inline fun <reified T> State<T>.selector(
    serializer: KSerializer<T> = serializer(),
    crossinline block: suspend (T) -> Unit,
) {
    selector(
        value = value,
        serializer = serializer
    ) { block(it) }
}

@Composable
inline fun <reified T> selector(
    value: T,
    serializer: KSerializer<T> = serializer(),
    crossinline block: suspend (T) -> Unit,
) {
    var last by rememberSerializable(stateSerializer = serializer) { mutableStateOf(value) }

    LaunchedEffect(value, last) {
        if (value != last) {
            last = value
            block(value)
        } else {
            last = value
        }
    }
}
