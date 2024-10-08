package kosh.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.seiko.imageloader.LocalImageLoader
import kosh.app.di.ApplicationScope
import kosh.app.di.IosWindowScope
import kosh.presentation.core.defaultAppContext
import kosh.presentation.di.DefaultRouteContext
import kosh.presentation.di.LocalRouteContext
import kosh.presentation.di.LocalRouteScopeFactory
import kosh.ui.component.path.LocalPathResolver
import kosh.ui.component.path.PathResolver
import kosh.ui.navigation.LocalRootNavigator
import kosh.ui.navigation.RootNavigator
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.stack.DefaultStackRouter
import kotlinx.serialization.serializer
import platform.UIKit.UIViewController

fun rootViewController(
    applicationScope: ApplicationScope,
) = rootViewController(applicationScope, RootRoute.Home())

fun rootViewController(
    applicationScope: ApplicationScope,
    start: RootRoute,
): UIViewController {

    val windowScope = IosWindowScope(
        applicationScope = applicationScope
    )

    Logger.setTag("[K]")
    Logger.d { "isDebug=${applicationScope.appComponent.debug}" }
    if (applicationScope.appComponent.debug) {
        Logger.setMinSeverity(Severity.Verbose)
//        Logger.setLogWriters(NSLogWriter())
    } else {
        Logger.setMinSeverity(Severity.Verbose)
//        Logger.setLogWriters(NSLogWriter())
    }

    val controllerDelegate = ComposeControllerDelegate()
    val backDispatcher = BackDispatcher()

    val routeContext = DefaultRouteContext(
        defaultAppContext(
            DefaultComponentContext(
                lifecycle = controllerDelegate.registry,
                backHandler = backDispatcher
            )
        )
    )

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

    val rootNavigator = RootNavigator { rootRouter.pushNew(it) }
    val pathResolver = PathResolver { applicationScope.appRepositoriesComponent.fileRepo.read(it) }

    return ComposeUIViewController(
        configure = {
            delegate = controllerDelegate
        }
    ) {
        viewModelFactory { initializer { controllerDelegate } }

        CompositionLocalProvider(
            LocalRouteContext provides routeContext,
            LocalRouteScopeFactory provides windowScope.routeScopeFactory,
            LocalImageLoader provides applicationScope.imageComponent.imageLoader,
            LocalPathResolver provides pathResolver,
            LocalRootNavigator provides rootNavigator,
        ) {
            val hapticFeedback = LocalHapticFeedback.current
            val stack by rootRouter.stack.subscribeAsState()

            PredictiveBackGestureOverlay(
                modifier = Modifier.fillMaxSize(),
                backDispatcher = backDispatcher,
                backIcon = null,
                endEdgeEnabled = false,
                startEdgeEnabled = derivedStateOf { stack.backStack.isNotEmpty() }.value,
                onClose = { hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress) },
                activationOffsetThreshold = 8.dp
            ) {
                App(
                    stackRouter = rootRouter,
                )
            }
        }
    }
}
