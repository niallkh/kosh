package kosh.presentation.di

import androidx.compose.runtime.Composable
import kosh.domain.AppRepositoriesComponent
import kosh.domain.DomainComponent
import kosh.domain.WindowRepositoriesComponent
import kotlinx.serialization.cbor.Cbor

interface RouteScope {

    val cbor: Cbor

    val domain: DomainComponent

    val appRepositories: AppRepositoriesComponent

    val windowRepositories: WindowRepositoriesComponent
}

@Composable
fun rememberRouteScope(): RouteScope {
    val routeScopeFactory = LocalRouteScopeFactory.current
    return rememberOnRoute {
        routeScopeFactory()
    }
}

@Composable
inline fun <reified VM : Any> di(
    crossinline block: RouteScope.() -> VM,
): VM = rememberRouteScope().block()
