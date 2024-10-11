package kosh.app.di

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.statekeeper.StateKeeper
import kosh.app.di.impl.DefaultUiScope
import kosh.presentation.core.UiContext
import kosh.presentation.core.defaultUiContext
import kosh.presentation.di.DeeplinkHandler

public fun iosUiContext(
    appScope: AppScope,
    lifecycle: Lifecycle,
    stateKeeper: StateKeeper,
): UiContext = defaultUiContext(
    componentContext = DefaultComponentContext(
        lifecycle = lifecycle,
        stateKeeper = stateKeeper,
    ),
    uiScope = DefaultUiScope(
        appScope = appScope,
        uiRepositoriesComponent = IosUiRepositoriesComponent(),
        deeplinkHandler = DeeplinkHandler(),
    )
)
