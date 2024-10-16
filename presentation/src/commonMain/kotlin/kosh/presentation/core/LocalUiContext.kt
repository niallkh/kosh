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

inline fun <reified T : Any> UiContext.getOrCreate(
    key: String,
    factory: () -> T,
): T {
    var instance: T? = container[key] as T?
    if (instance == null) {
        instance = factory()
        container[key] = instance
    }
    return instance
}
