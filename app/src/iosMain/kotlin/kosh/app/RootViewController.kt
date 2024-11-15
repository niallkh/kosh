package kosh.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.seiko.imageloader.LocalImageLoader
import kosh.app.di.AppScope
import kosh.app.di.IosAppScope
import kosh.presentation.core.LocalPresentationContext
import kosh.presentation.core.PresentationContext
import kosh.ui.component.scaffold.LocalSnackbarHostState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import platform.Foundation.NSURL.Companion.URLWithString
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

public fun rootViewController(
    appScope: AppScope,
    presentationContext: PresentationContext,
): UIViewController {

    requestNotificationPermission()
    val pushNotifier = (appScope as IosAppScope).iosPushNotifier

    return ComposeUIViewController {
        CompositionLocalProvider(
            LocalPresentationContext provides presentationContext,
            LocalImageLoader provides appScope.imageComponent.imageLoader,
        ) {

            PredictiveBackGestureOverlay(
                modifier = Modifier.fillMaxSize(),
                backDispatcher = presentationContext.backHandler as BackDispatcher,
                backIcon = null,
                endEdgeEnabled = false,
                activationOffsetThreshold = 8.dp
            ) {
                Box {
                    val snackbar = LocalSnackbarHostState.current
                    val scope = rememberCoroutineScope()

                    App(
                        initialLink = null,
                        onResult = {
                            if (!it.redirect.isNullOrEmpty()) {
                                UIApplication.sharedApplication.openURL(
                                    url = URLWithString(it.redirect!!)!!,
                                    options = emptyMap<Any?, Any>(),
                                    completionHandler = {
                                        presentationContext.presentationScope
                                            .deeplinkHandler.handle(null)
                                    }
                                )
                            } else {
                                presentationContext.presentationScope.deeplinkHandler.handle(null)
                                scope.launch {
                                    delay(300)
                                    snackbar.showSnackbar(
                                        "Done! You may return to previous app."
                                    )
                                }
                            }
                        }
                    )

                    LaunchedEffect(Unit) {
                        for (uri in pushNotifier.uris) {
                            presentationContext.presentationScope.deeplinkHandler.handle(uri)
                        }
                    }
                }
            }
        }
    }
}
