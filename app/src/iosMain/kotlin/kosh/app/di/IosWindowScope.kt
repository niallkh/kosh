package kosh.app.di

import kosh.app.di.impl.DefaultRouteScopeFactory
import kosh.domain.WindowRepositoriesComponent
import kosh.domain.core.provider
import kosh.presentation.di.RouteScopeFactory

class IosWindowScope(
    applicationScope: ApplicationScope,
) : WindowScope {

    override val routeScopeFactory: RouteScopeFactory by provider {
        DefaultRouteScopeFactory(
            applicationScope = applicationScope,
            windowScope = this
        )
    }

    override val windowRepositoriesComponent: WindowRepositoriesComponent by provider {
        IosWindowRepositoriesComponent()
    }
}
