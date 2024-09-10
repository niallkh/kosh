package kosh.app.di

import kosh.app.di.impl.DefaultDomainComponent
import kosh.domain.AppRepositoriesComponent
import kosh.domain.DomainComponent
import kosh.domain.WindowRepositoriesComponent
import kosh.domain.core.provider
import kosh.presentation.di.RouteScope
import kotlinx.serialization.cbor.Cbor

class AndroidRouteScope(
    applicationScope: ApplicationScope,
    windowScope: WindowScope,
) : RouteScope, WindowScope by windowScope, ApplicationScope by applicationScope {

    override val cbor: Cbor by provider {
        serializationComponent.cbor
    }

    override val serializationComponent: SerializationComponent by provider {
        windowScope.serializationComponent
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
