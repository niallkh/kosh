package kosh.app.di

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.statekeeper.StateKeeper
import kosh.app.di.impl.DefaultRouteScopeFactory
import kosh.domain.WindowRepositoriesComponent
import kosh.domain.core.provider
import kosh.presentation.core.AppContext
import kosh.presentation.core.defaultAppContext
import kosh.presentation.di.RouteScopeFactory

public class IosWindowScope(
    applicationScope: AppScope,
    lifecycle: Lifecycle,
    stateKeeper: StateKeeper,
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

    override val appContext: AppContext by provider {
        defaultAppContext(
            componentContext = DefaultComponentContext(
                lifecycle = lifecycle,
                stateKeeper = stateKeeper,
            )
        )
    }

    override val deeplinkHandler: DeeplinkHandler by provider {
        DeeplinkHandler()
    }
}
