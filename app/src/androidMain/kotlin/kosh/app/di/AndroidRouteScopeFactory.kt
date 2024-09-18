package kosh.app.di

import kosh.presentation.di.RouteScope
import kosh.presentation.di.RouteScopeFactory

class AndroidRouteScopeFactory(
    private val applicationScope: ApplicationScope,
    private val windowScope: WindowScope,
) : RouteScopeFactory {
    override fun invoke(): RouteScope = AndroidRouteScope(
        applicationScope = applicationScope,
        windowScope = windowScope,
    )
}
