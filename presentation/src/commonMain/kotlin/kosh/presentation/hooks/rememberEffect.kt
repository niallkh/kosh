package kosh.presentation.hooks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
inline fun rememberEffect(
    vararg keys: Any,
    crossinline effect: suspend () -> Unit,
) {
    LaunchedEffect(keys) {
        effect()
    }
}
