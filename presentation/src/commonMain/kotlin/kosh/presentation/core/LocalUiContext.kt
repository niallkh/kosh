package kosh.presentation.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import kosh.presentation.di.UiScope

val LocalUiContext: ProvidableCompositionLocal<UiContext> = compositionLocalOf {
    error("UiContext not provided")
}

@Composable
inline fun <reified VM : Any> di(
    crossinline block: UiScope.() -> VM,
): VM = LocalUiContext.current.uiScope.block()
