package kosh.presentation.di

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

fun interface RouteScopeFactory {
    operator fun invoke(): RouteScope
}

val LocalRouteScopeFactory: ProvidableCompositionLocal<RouteScopeFactory> =
    staticCompositionLocalOf { error("RouteScopeFactory not provided") }
