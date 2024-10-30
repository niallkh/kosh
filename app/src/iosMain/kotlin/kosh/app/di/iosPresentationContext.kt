package kosh.app.di

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.statekeeper.StateKeeper
import kosh.app.di.impl.DefaultPresentationScope
import kosh.presentation.core.PresentationContext
import kosh.presentation.core.defaultPresentationContext
import kosh.presentation.di.DeeplinkHandler

public fun iosPresentationContext(
    appScope: AppScope,
    lifecycle: Lifecycle,
    stateKeeper: StateKeeper,
): PresentationContext = defaultPresentationContext(
    componentContext = DefaultComponentContext(
        lifecycle = lifecycle,
        stateKeeper = stateKeeper,
    ),
    presentationScope = DefaultPresentationScope(
        appScope = appScope,
        uiRepositoriesComponent = IosUiRepositoriesComponent(),
        deeplinkHandler = DeeplinkHandler(),
    )
)
