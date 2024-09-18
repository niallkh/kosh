package kosh.app.di

import android.content.ContentResolver
import androidx.activity.result.ActivityResultRegistry
import kosh.app.di.impl.AndroidWindowRepositoriesComponent
import kosh.domain.WindowRepositoriesComponent
import kosh.domain.core.provider
import kosh.presentation.di.RouteScopeFactory

class AndroidWindowScope(
    applicationScope: ApplicationScope,
    activityResultRegistry: ActivityResultRegistry,
    contentResolver: ContentResolver,
) : WindowScope, ApplicationScope by applicationScope {

    override val windowRepositoriesComponent: WindowRepositoriesComponent by provider {
        AndroidWindowRepositoriesComponent(
            contentResolver = contentResolver,
            activityResultRegistry = activityResultRegistry,
        )
    }

    override val routeScopeFactory: RouteScopeFactory by provider {
        AndroidRouteScopeFactory(
            applicationScope = applicationScope,
            windowScope = this
        )
    }
}
