package kosh.ui.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kosh.presentation.di.rememberRouteScope
import kosh.ui.navigation.routes.Route

@Composable
fun <R : Route> LogScreen(config: R) {
    val routeScope = rememberRouteScope()
    DisposableEffect(config) {
        routeScope.appRepositories.analyticsRepo.logScreen(checkNotNull(config::class.qualifiedName))
        onDispose { }
    }
}
