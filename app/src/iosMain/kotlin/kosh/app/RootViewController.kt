package kosh.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
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
import kosh.app.di.WindowScope
import kosh.presentation.app.rememberInitApp
import kosh.presentation.di.DefaultRouteContext
import kosh.presentation.di.LocalRouteContext
import kosh.presentation.di.LocalRouteScopeFactory
import kosh.ui.component.path.LocalPathResolver
import kosh.ui.component.path.PathResolver
import kosh.ui.navigation.LocalRootNavigator
import kosh.ui.navigation.RootNavigator
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.parseDeeplink
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.stack.DefaultStackRouter
import kotlinx.serialization.serializer
import platform.UIKit.UIViewController

fun rootViewController(
    appScope: AppScope,
    windowScope: WindowScope,
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

    val routeContext = DefaultRouteContext(windowScope.appContext)

    val rootRouter = DefaultStackRouter(
        routeContext = routeContext,
        serializer = serializer(),
        start = start,
        link = null,
        onResult = {
            when (it) {
                is RouteResult.Canceled -> reset()
                is RouteResult.Finished -> reset()
                is RouteResult.Up -> when (val route = it.route) {
                    null -> replaceAll(start)
                    else -> replaceAll(start, route)
                }
            }
        },
    )

    windowScope.deeplinkHandler.subscribe {
        parseDeeplink(it)?.let(rootRouter::handle)
    }

    val rootNavigator = RootNavigator { rootRouter.pushNew(it) }
    val pathResolver = PathResolver { appScope.appRepositoriesComponent.fileRepo.read(it) }

    return ComposeUIViewController {
        CompositionLocalProvider(
            LocalRouteContext provides routeContext,
            LocalRouteScopeFactory provides windowScope.routeScopeFactory,
            LocalImageLoader provides appScope.imageComponent.imageLoader,
            LocalPathResolver provides pathResolver,
            LocalRootNavigator provides rootNavigator,
        ) {
            val hapticFeedback = LocalHapticFeedback.current

            PredictiveBackGestureOverlay(
                modifier = Modifier.fillMaxSize(),
                backDispatcher = windowScope.appContext.backHandler as BackDispatcher,
                backIcon = null,
                endEdgeEnabled = false,
                onClose = { hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress) },
                activationOffsetThreshold = 8.dp
            ) {
                val (loaded) = rememberInitApp()

                if (loaded) {
                    App(
                        stackRouter = rootRouter,
                    )
                }
            }
        }
    }
}
