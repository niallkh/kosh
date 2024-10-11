package kosh.app

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import co.touchlab.kermit.Logger
import co.touchlab.kermit.NSLogWriter
import co.touchlab.kermit.Severity
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.seiko.imageloader.LocalImageLoader
import kosh.app.di.AppScope
import kosh.presentation.app.rememberInitApp
import kosh.presentation.core.LocalUiContext
import kosh.presentation.core.UiContext
import kosh.ui.component.path.LocalPathResolver
import kosh.ui.component.path.PathResolver
import kosh.ui.navigation.LocalRootNavigator
import kosh.ui.navigation.RootNavigator
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.parseDeeplink
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.stack.DefaultStackRouter
import kotlinx.serialization.serializer
import platform.Foundation.NSURL.Companion.URLWithString
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

public fun rootViewController(
    appScope: AppScope,
    uiContext: UiContext,
): UIViewController {
    val start: RootRoute = RootRoute.Home()

    Logger.setTag("[K]")
    if (appScope.appComponent.debug) {
        Logger.setMinSeverity(Severity.Verbose)
        Logger.setLogWriters(NSLogWriter())
    } else {
        Logger.setMinSeverity(Severity.Verbose)
        Logger.setLogWriters(NSLogWriter())
    }

    Logger.d { "isDebug=${appScope.appComponent.debug}" }

    val rootRouter = DefaultStackRouter(
        uiContext = uiContext,
        serializer = serializer(),
        start = start,
        link = null,
        onResult = {
            when (it) {
                is RouteResult.Result -> {
                    if (it.redirect.isNullOrEmpty()) {
                        handle(null)
                    } else {
                        UIApplication.sharedApplication.openURL(
                            url = URLWithString(it.redirect!!)!!,
                            options = emptyMap<Any?, Any>(),
                            completionHandler = { redirected ->
                                if (!redirected) {
                                    handle(null)
                                }
                            }
                        )
                    }
                }

                is RouteResult.Up -> when (val route = it.route) {
                    null -> replaceAll(start)
                    else -> replaceAll(start, route)
                }
            }
        },
    )

    val rootNavigator = RootNavigator { rootRouter.pushNew(it) }
    val pathResolver = PathResolver { appScope.appRepositoriesComponent.fileRepo.read(it) }
    uiContext.uiScope.deeplinkHandler.subscribe { it?.let(::parseDeeplink).let(rootRouter::handle) }

    return ComposeUIViewController {
        CompositionLocalProvider(
            LocalUiContext provides uiContext,
            LocalImageLoader provides appScope.imageComponent.imageLoader,
            LocalPathResolver provides pathResolver,
            LocalRootNavigator provides rootNavigator,
        ) {

            PredictiveBackGestureOverlay(
                modifier = Modifier.fillMaxSize(),
                backDispatcher = uiContext.backHandler as BackDispatcher,
                backIcon = null,
                endEdgeEnabled = false,
                activationOffsetThreshold = 8.dp
            ) {
                val (loaded) = rememberInitApp()

                if (loaded) {
                    App(
                        stackRouter = rootRouter,
                    )
                } else {
                    SplashScreen()
                }
            }
        }
    }
}

@Composable
private fun SplashScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(
                if (isSystemInDarkTheme()) Color(0xFF1C1C1E)
                else Color(0xFFF2F2F7)
            )
    )
}
