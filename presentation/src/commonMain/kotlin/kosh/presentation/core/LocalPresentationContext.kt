package kosh.presentation.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import kosh.presentation.di.PresentationScope

val LocalPresentationContext: ProvidableCompositionLocal<PresentationContext> = compositionLocalOf {
    error("PresentationContext not provided")
}

@Composable
inline fun <reified VM : Any> di(
    crossinline block: PresentationScope.() -> VM,
): VM = LocalPresentationContext.current.presentationScope.block()

inline fun <reified T : Any> PresentationContext.getOrCreate(
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
