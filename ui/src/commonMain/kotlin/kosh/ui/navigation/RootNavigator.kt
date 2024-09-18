package kosh.ui.navigation

import androidx.compose.runtime.compositionLocalOf
import kosh.ui.navigation.routes.RootRoute

fun interface RootNavigator {
    fun open(rootRoute: RootRoute)
}

val LocalRootNavigator = compositionLocalOf<RootNavigator> {
    error("LocalRootNavigator not provided")
}
