package kosh.app.di

import androidx.activity.ComponentActivity
import com.arkivanov.decompose.defaultComponentContext
import kosh.app.di.impl.DefaultUiScope
import kosh.presentation.core.UiContext
import kosh.presentation.core.defaultUiContext
import kosh.presentation.di.DeeplinkHandler

public fun androidUiContext(
    applicationScope: AppScope,
    activity: ComponentActivity,
): UiContext = defaultUiContext(
    componentContext = activity.defaultComponentContext(),
    uiScope = DefaultUiScope(
        appScope = applicationScope,
        uiRepositoriesComponent = AndroidUiRepositoriesComponent(
            contentResolver = activity.contentResolver,
            activityResultRegistry = activity.activityResultRegistry,
        ),
        deeplinkHandler = DeeplinkHandler()
    )
)
