package kosh.app

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import co.touchlab.kermit.Logger
import co.touchlab.kermit.NSLogWriter
import co.touchlab.kermit.Severity
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.push
import com.seiko.imageloader.LocalImageLoader
import kosh.app.di.IosApplicationScope
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
    root: ComponentContext,
): UIViewController {

    val applicationScope = IosApplicationScope()

    Logger.setTag("[K]")

    if (applicationScope.appComponent.debug) {
        Logger.setMinSeverity(Severity.Verbose)
        Logger.setLogWriters(NSLogWriter())
    } else {
        Logger.setMinSeverity(Severity.Info)
        Logger.setLogWriters(NSLogWriter())
    }

    val routeContext = DefaultRouteContext(defaultAppContext(root))

    val rootRouter = DefaultStackRouter(
        routeContext = routeContext,
        serializer = serializer(),
        start = RootRoute.Home() as RootRoute,
        link = null,
        onResult = {
            when (it) {
                is RouteResult.Canceled -> {}
                is RouteResult.Finished -> {}
                is RouteResult.Up -> {}
            }
        },
    )

    val rootNavigator = RootNavigator { rootRouter.push(it) }
    val pathResolver = PathResolver { applicationScope.appRepositoriesComponent.fileRepo.read(it) }

    return ComposeUIViewController {
        CompositionLocalProvider(
            LocalRouteContext provides routeContext,
            LocalRouteScopeFactory provides applicationScope.routeScopeFactory,
            LocalImageLoader provides applicationScope.imageComponent.imageLoader,
            LocalPathResolver provides pathResolver,
            LocalRootNavigator provides rootNavigator,
        ) {
            App(
                stackRouter = rootRouter,
            )
        }
    }
}
