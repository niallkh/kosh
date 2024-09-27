package kosh.app

import android.Manifest
import android.content.Intent
import android.net.Uri
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
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.lifecycle.asEssentyLifecycle
import com.arkivanov.essenty.lifecycle.doOnStop
import com.eygraber.uri.toUri
import com.seiko.imageloader.LocalImageLoader
import kosh.app.di.AndroidWindowScope
import kosh.presentation.app.rememberInitApp
import kosh.presentation.core.defaultAppContext
import kosh.presentation.di.DefaultRouteContext
import kosh.presentation.di.LocalRouteContext
import kosh.presentation.di.LocalRouteScopeFactory
import kosh.ui.component.path.LocalPathResolver
import kosh.ui.component.path.PathResolver
import kosh.ui.navigation.LocalRootNavigator
import kosh.ui.navigation.RootNavigator
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.deeplink
import kosh.ui.navigation.parseDeeplink
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.stack.DefaultStackRouter
import kosh.ui.navigation.stack.StackRouter
import kotlinx.serialization.serializer

class KoshActivity : FragmentActivity() {

    private val logger = Logger.withTag("[K]KoshActivity")

    private lateinit var rootRouter: StackRouter<RootRoute>

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        logger.d { "onCreate()" }
        splashScreen.setKeepOnScreenCondition { true }

        val deeplink = handleDeepLink(intent) { it?.toUri()?.let(::parseDeeplink) }
        logger.v { "deeplink=${intent.data}" }

        val routeContext = DefaultRouteContext(defaultAppContext(defaultComponentContext()))

        val windowScope = AndroidWindowScope(
            applicationScope = KoshApplication.applicationScope,
            activityResultRegistry = activityResultRegistry,
            contentResolver = contentResolver,
        )

        rootRouter = DefaultStackRouter(
            routeContext = routeContext,
            serializer = serializer(),
            start = RootRoute.Home(),
            link = deeplink,
            onResult = {
                when (it) {
                    is RouteResult.Canceled -> onCancel()
                    is RouteResult.Finished -> onFinish()
                    is RouteResult.Up -> onNavigateUp(it.route)
                }
            },
        )

        val rootNavigator = RootNavigator { rootRouter.push(it) }
        val pathResolver = PathResolver { windowScope.appRepositoriesComponent.fileRepo.read(it) }
        setContent {
            CompositionLocalProvider(
                LocalRouteContext provides routeContext,
                LocalRouteScopeFactory provides windowScope.routeScopeFactory,
                LocalImageLoader provides windowScope.imageComponent.imageLoader,
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
        logger.v { "deeplink=${intent.data}" }

        val deeplink = intent.data?.toUri()?.let(::parseDeeplink)
        deeplink?.let { rootRouter.handle(it) }
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

    private fun onFinish() {
        logger.i { "onFinish()" }
        if (moveTaskToBack(false)) {
            lifecycle.asEssentyLifecycle().doOnStop(isOneTime = true) {
                rootRouter.reset()
            }
        } else {
            finish()
        }
    }

    private fun onCancel() {
        logger.i { "onCancel()" }
        if (moveTaskToBack(false)) {
            lifecycle.asEssentyLifecycle().doOnStop(isOneTime = true) {
                rootRouter.reset()
            }
        } else {
            finish()
        }
    }

    private fun onNavigateUp(rootRoute: RootRoute?) {
        val intent = Intent(
            this,
            KoshActivity::class.java
        ).apply {
            data = Uri.parse(deeplink(rootRoute).toString())
        }

        TaskStackBuilder.create(this)
            .addNextIntentWithParentStack(intent)
            .startActivities()

        finish()
    }

    private fun <T> handleDeepLink(
        intent: Intent,
        block: (Uri?) -> T,
    ): T? {
        val savedState: Bundle? =
            savedStateRegistry.consumeRestoredStateForKey(key = KEY_SAVED_DEEP_LINK_STATE)
        val isDeepLinkHandled = savedState?.getBoolean(KEY_DEEP_LINK_HANDLED) ?: false
        val deepLink = intent.data.takeUnless { isDeepLinkHandled }

        savedStateRegistry.registerSavedStateProvider(key = KEY_SAVED_DEEP_LINK_STATE) {
            bundleOf(KEY_DEEP_LINK_HANDLED to (isDeepLinkHandled || (deepLink != null)))
        }

        return block(deepLink)
    }

    companion object {
        private const val KEY_SAVED_DEEP_LINK_STATE = "SAVED_DEEP_LINK_STATE"
        private const val KEY_DEEP_LINK_HANDLED = "DEEP_LINK_HANDLED"
    }

    override fun onDestroy() {
        viewModelStore.clear()
        super.onDestroy()
    }
}
