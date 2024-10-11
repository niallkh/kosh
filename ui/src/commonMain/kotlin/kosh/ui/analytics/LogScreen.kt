package kosh.ui.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kosh.presentation.core.di
import kosh.ui.navigation.routes.Route

@Composable
fun <R : Route> LogScreen(config: R) {
    val analyticsRepo = di { appRepositoriesComponent.analyticsRepo }

    DisposableEffect(config) {
        analyticsRepo.logScreen(checkNotNull(config::class.qualifiedName))
        onDispose { }
    }
}
