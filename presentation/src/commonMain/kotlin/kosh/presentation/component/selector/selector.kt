package kosh.presentation.component.selector

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kosh.presentation.di.rememberRetained

@Composable
fun <T> State<T>.selector(
    block: suspend (T) -> Unit,
) {
    var last by rememberRetained { mutableStateOf(value) }

    LaunchedEffect(value, last) {
        if (value != last) {
            last = value
            block(value)
        } else {
            last = value
        }
    }
}
