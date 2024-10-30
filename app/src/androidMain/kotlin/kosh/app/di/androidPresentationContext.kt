package kosh.app.di

import androidx.activity.ComponentActivity
import com.arkivanov.decompose.defaultComponentContext
import kosh.app.di.impl.DefaultPresentationScope
import kosh.presentation.core.PresentationContext
import kosh.presentation.core.defaultPresentationContext
import kosh.presentation.di.DeeplinkHandler

public fun androidPresentationContext(
    applicationScope: AppScope,
    activity: ComponentActivity,
): PresentationContext = defaultPresentationContext(
    componentContext = activity.defaultComponentContext(),
    presentationScope = DefaultPresentationScope(
        appScope = applicationScope,
        uiRepositoriesComponent = AndroidUiRepositoriesComponent(
            contentResolver = activity.contentResolver,
            activityResultRegistry = activity.activityResultRegistry,
        ),
        deeplinkHandler = DeeplinkHandler()
    )
)
