package kosh.app.di.impl

import kosh.app.di.AppScope
import kosh.domain.AppRepositoriesComponent
import kosh.domain.DomainComponent
import kosh.domain.UiRepositoriesComponent
import kosh.domain.core.provider
import kosh.presentation.di.DeeplinkHandler
import kosh.presentation.di.PresentationScope
import kotlinx.serialization.cbor.Cbor

internal class DefaultPresentationScope(
    private val appScope: AppScope,
    override val uiRepositoriesComponent: UiRepositoriesComponent,
    override val deeplinkHandler: DeeplinkHandler,
) : PresentationScope {

    override val cbor: Cbor by provider {
        appScope.serializationComponent.cbor
    }

    override val appRepositoriesComponent: AppRepositoriesComponent
        get() = appScope.appRepositoriesComponent

    override val domain: DomainComponent by provider {
        DefaultDomainComponent(
            uiRepositoriesComponent = uiRepositoriesComponent,
            appRepositoriesComponent = appRepositoriesComponent,
            applicationScope = appScope.coroutinesComponent.applicationScope,
        )
    }

    override fun create(): PresentationScope = DefaultPresentationScope(
        appScope = appScope,
        uiRepositoriesComponent = uiRepositoriesComponent,
        deeplinkHandler = deeplinkHandler,
    )
}