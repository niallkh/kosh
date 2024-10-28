package kosh.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.seiko.imageloader.LocalImageLoader
import kosh.app.di.AppScope
import kosh.app.di.IosAppScope
import kosh.presentation.core.LocalUiContext
import kosh.presentation.core.UiContext
import kosh.ui.component.path.LocalPathResolver
import kosh.ui.component.path.PathResolver
import kosh.ui.navigation.parseDeeplink
import kosh.ui.navigation.routes.RootRoute
import platform.Foundation.NSURL.Companion.URLWithString
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

public fun rootViewController(
    appScope: AppScope,
    uiContext: UiContext,
): UIViewController {
    var link by mutableStateOf<RootRoute?>(null)
    val pathResolver = PathResolver { appScope.appRepositoriesComponent.fileRepo.read(it) }
    requestNotificationPermission()
    val pushNotifier = (appScope as IosAppScope).iosPushNotifier

    return ComposeUIViewController {
        CompositionLocalProvider(
            LocalUiContext provides uiContext,
            LocalImageLoader provides appScope.imageComponent.imageLoader,
            LocalPathResolver provides pathResolver,
        ) {

            PredictiveBackGestureOverlay(
                modifier = Modifier.fillMaxSize(),
                backDispatcher = uiContext.backHandler as BackDispatcher,
                backIcon = null,
                endEdgeEnabled = false,
                activationOffsetThreshold = 8.dp
            ) {
                Box {
                    App(
                        link = link,
                        onResult = {
                            Logger.d { "RouteResult.Result: ${it.redirect}" }
                            if (it.redirect.isNullOrEmpty()) {
                                link = null
                            } else {
                                UIApplication.sharedApplication.openURL(
                                    url = URLWithString(it.redirect!!)!!,
                                    options = emptyMap<Any?, Any>(),
                                    completionHandler = { redirected ->
                                        if (!redirected) {
                                            link = null
                                        }
                                    }
                                )
                            }
                        }
                    )

                    LaunchedEffect(Unit) {
                        for (uri in pushNotifier.uris) {
                            parseDeeplink(uri)?.let {
                                link = it
                            }
                        }
                    }
                }
            }
        }
    }
}
