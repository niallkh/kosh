package kosh.presentation.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.remember
import kosh.presentation.core.LocalUiContext
import kosh.presentation.core.UiContext
import kotlin.reflect.typeOf


@Composable
inline fun <reified T : Any> rememberOnRoute(
    crossinline factory: @DisallowComposableCalls UiContext.() -> T,
): T {
    val routerContext = LocalUiContext.current
    return remember {
        routerContext.getOrCreate {
            factory()
        }
    }
}

inline fun <reified T : Any> UiContext.getOrCreate(
    factory: UiContext.() -> T,
): T {
    var instance: T? = container[typeOf<T>()] as T?
    if (instance == null) {
        instance = factory()
        container[typeOf<T>()] = instance
    }
    return instance
}


