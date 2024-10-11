package kosh.app

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.essenty.lifecycle.asEssentyLifecycle
import com.arkivanov.essenty.lifecycle.doOnStop
import com.seiko.imageloader.LocalImageLoader
import kosh.app.di.AndroidWindowScope
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
import kosh.ui.navigation.stack.StackRouter
import kotlinx.serialization.serializer
import java.net.URLDecoder

class KoshActivity : FragmentActivity() {

    private val logger = Logger.withTag("[K]KoshActivity")

    private lateinit var windowScope: WindowScope
    private lateinit var rootRouter: StackRouter<RootRoute>

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        logger.d { "onCreate()" }
        splashScreen.setKeepOnScreenCondition { true }

        val deeplink = intent?.data?.toString()?.let(::parseDeeplink)

        logger.v { "action=${intent.action}, deeplink=${intent.data}" }

        val appScope = KoshApplication.appScope
        windowScope = AndroidWindowScope(
            applicationScope = appScope,
            activity = this,
        )

        URLDecoder.decode("", "UTF-8")

        val routeContext = DefaultRouteContext(windowScope.appContext)

        val start = RootRoute.Home()
        val rootRouter = DefaultStackRouter(
            routeContext = routeContext,
            serializer = serializer(),
            start = start,
            link = deeplink,
            onResult = {
                when (it) {
                    is RouteResult.Result -> onResult()
                    is RouteResult.Up -> when (val route = it.route) {
                        null -> replaceAll(start)
                        else -> replaceAll(start, route)
                    }
                }
            },
        )
        this.rootRouter = rootRouter

        val rootNavigator = RootNavigator { rootRouter.pushNew(it) }
        val pathResolver = PathResolver { appScope.appRepositoriesComponent.fileRepo.read(it) }
        windowScope.deeplinkHandler.subscribe { it?.let(::parseDeeplink).let(rootRouter::handle) }

        setContent {
            CompositionLocalProvider(
                LocalRouteContext provides routeContext,
                LocalRouteScopeFactory provides windowScope.routeScopeFactory,
                LocalImageLoader provides appScope.imageComponent.imageLoader,
                LocalPathResolver provides pathResolver,
                LocalRootNavigator provides rootNavigator,
            ) {
                App(
                    stackRouter = rootRouter,
                )

                NotificationPermission()
                BluetoothPermission()
                HideSplashScreen(splashScreen)
                EdgeToEdge()
            }
        }
    }

    @Composable
    private fun NotificationPermission() {
        val launcher = rememberLauncherForActivityResult(RequestPermission()) {
            logger.v { "NotificationPermission() = $it" }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            LaunchedEffect(Unit) {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    @Composable
    private fun BluetoothPermission() {
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            logger.v { "BluetoothPermission() = $it" }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            LaunchedEffect(Unit) {
                launcher.launch(
                    arrayOf(
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT,
                    )
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val intent = Intent(this, KoshService::class.java)
        ContextCompat.startForegroundService(this, intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        logger.d { "onNewIntent()" }
        logger.v { "action=${intent.action}, deeplink=${intent.data}" }

        intent.data?.toString()?.let {
            windowScope.deeplinkHandler.handle(it)
        }
    }

    @Composable
    private fun HideSplashScreen(splashScreen: SplashScreen) {
        val (loaded) = rememberInitApp()

        LaunchedEffect(loaded) {
            splashScreen.setKeepOnScreenCondition { !loaded }
        }
    }

    @Composable
    private fun EdgeToEdge() {
        val darkTheme = isSystemInDarkTheme()

        LaunchedEffect(darkTheme) {
            enableEdgeToEdge()
        }
    }

    private fun onResult() {
        logger.i { "onResult()" }
        if (intent.action == Intent.ACTION_MAIN && moveTaskToBack(false)) {
            lifecycle.asEssentyLifecycle().doOnStop(isOneTime = true) {
                windowScope.deeplinkHandler.handle(null)
            }
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        logger.d { "onDestroy()" }
        super.onDestroy()
    }
}
