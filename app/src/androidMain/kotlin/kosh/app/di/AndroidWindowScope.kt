package kosh.app.di

import androidx.activity.ComponentActivity
import com.arkivanov.decompose.defaultComponentContext
import kosh.app.di.impl.DefaultRouteScopeFactory
import kosh.domain.WindowRepositoriesComponent
import kosh.domain.core.provider
import kosh.presentation.core.AppContext
import kosh.presentation.core.defaultAppContext
import kosh.presentation.di.RouteScopeFactory

class AndroidWindowScope(
    applicationScope: AppScope,
    activity: ComponentActivity,
) : WindowScope {

    override val windowRepositoriesComponent: WindowRepositoriesComponent by provider {
        AndroidWindowRepositoriesComponent(
            contentResolver = activity.contentResolver,
            activityResultRegistry = activity.activityResultRegistry,
        )
    }

    override val routeScopeFactory: RouteScopeFactory by provider {
        DefaultRouteScopeFactory(
            applicationScope = applicationScope,
            windowScope = this
        )
    }

    override val appContext: AppContext by provider {
        defaultAppContext(activity.defaultComponentContext())
    }

    override val deeplinkHandler: DeeplinkHandler by provider {
        DeeplinkHandler()
    }
}
