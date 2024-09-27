package kosh.app.di.impl

import kosh.app.di.ApplicationScope
import kosh.app.di.SerializationComponent
import kosh.app.di.WindowScope
import kosh.domain.AppRepositoriesComponent
import kosh.domain.DomainComponent
import kosh.domain.WindowRepositoriesComponent
import kosh.domain.core.provider
import kosh.presentation.di.RouteScope
import kosh.presentation.di.RouteScopeFactory
import kotlinx.serialization.cbor.Cbor

class DefaultRouteScope(
    applicationScope: ApplicationScope,
    windowScope: WindowScope,
) : RouteScope,
    WindowScope by windowScope,
    ApplicationScope by applicationScope {

    override val cbor: Cbor by provider {
        serializationComponent.cbor
    }

    override val serializationComponent: SerializationComponent by provider {
        applicationScope.serializationComponent
    }

    override val domain: DomainComponent by provider {
        DefaultDomainComponent(
            windowRepositoriesComponent = windowRepositoriesComponent,
            appRepositoriesComponent = appRepositoriesComponent,
            applicationScope = coroutinesComponent.applicationScope,
        )
    }

    override val appRepositories: AppRepositoriesComponent
        get() = appRepositoriesComponent

    override val windowRepositories: WindowRepositoriesComponent
        get() = windowRepositoriesComponent
}

class DefaultRouteScopeFactory(
    private val applicationScope: ApplicationScope,
    private val windowScope: WindowScope,
) : RouteScopeFactory {
    override fun invoke(): RouteScope = DefaultRouteScope(
        applicationScope = applicationScope,
        windowScope = windowScope,
    )
}
