package kosh.presentation.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import kosh.presentation.core.AppContext
import kosh.presentation.core.RouteContext
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class DefaultRouteContext(
    appContext: AppContext,
) : RouteContext,
    AppContext by appContext,
    MutableMap<KType, Any> by mutableMapOf()

val LocalRouteContext: ProvidableCompositionLocal<RouteContext> = compositionLocalOf {
    error("RouteContext not provided")
}

@Composable
inline fun <reified T : Any> rememberOnRoute(
    crossinline factory: @DisallowComposableCalls RouteContext.() -> T,
): T {
    val routerContext = LocalRouteContext.current
    return remember {
        routerContext.getOrCreate {
            factory()
        }
    }
}

inline fun <reified T : Any> RouteContext.getOrCreate(
    factory: RouteContext.() -> T,
): T {
    var instance: T? = get(typeOf<T>()) as T?
    if (instance == null) {
        instance = factory()
        put(typeOf<T>(), instance)
    }
    return instance
}

inline fun <reified T> RouteContext.get(): T? =
    get(typeOf<T>()) as T?


