package kosh.ui.component.scaffold

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalSnackbarHostState: ProvidableCompositionLocal<SnackbarHostState> =
    compositionLocalOf { error("LocalSnackbarHostState not provided") }

val LocalSnackbarHostOffset: ProvidableCompositionLocal<MutableState<Dp>> =
    staticCompositionLocalOf { mutableStateOf(0.dp) }

@Composable
fun ProvideSnackbarOffset(y: Dp) {
    val snackBarOffset = LocalSnackbarHostOffset.current
    DisposableEffect(Unit) {
        snackBarOffset.value += y
        onDispose { snackBarOffset.value -= y }
    }
}
