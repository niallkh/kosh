package kosh.app

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.lifecycle.doOnStop
import com.eygraber.uri.toAndroidUri
import com.eygraber.uri.toUri
import com.seiko.imageloader.LocalImageLoader
import kosh.app.di.DefaultWindowScope
import kosh.presentation.app.rememberInitApp
import kosh.presentation.core.defaultAppContext
import kosh.presentation.di.DefaultRouteContext
import kosh.presentation.di.LocalRouteContext
import kosh.presentation.di.LocalRouteScopeFactory
import kosh.ui.app.ApplicationLifecycle
import kosh.ui.app.LocalApplicationLifecycle
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
import kotlin.LazyThreadSafetyMode.NONE

class KoshActivity : FragmentActivity() {

    private val logger = Logger.withTag("[K]KoshActivity")

    private val applicationScope by lazy(NONE) {
        KoshApplication.applicationScope
    }

    private val windowScope by lazy(NONE) {
        DefaultWindowScope(
            applicationScope = applicationScope,
            activityResultRegistry = activityResultRegistry,
            contentResolver = contentResolver,
        )
    }

    private val routeContext by lazy(NONE) {
        DefaultRouteContext(defaultAppContext(defaultComponentContext()))
    }

    private lateinit var rootRouter: StackRouter<RootRoute>

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        logger.d { "onCreate()" }
        splashScreen.setKeepOnScreenCondition { true }

        val deeplink = handleDeepLink(intent) { it?.toUri()?.let(::parseDeeplink) }
        logger.v { "deeplink=${intent.data}" }

        rootRouter = DefaultStackRouter(
            routeContext = routeContext,
            serializer = serializer(),
            start = RootRoute.Home(),
            link = deeplink,
            onResult = {
                when (it) {
                    is RouteResult.Canceled -> onCancel()
                    is RouteResult.Finished -> onFinish()
                    is RouteResult.Up -> navigateUp(it.route)
                }
            },
        )

        val rootNavigator = RootNavigator {
            rootRouter.push(it)
        }

        val applicationLifecycle = ApplicationLifecycle(routeContext.lifecycle)
        val fileRepo = windowScope.appRepositoriesComponent.fileRepo
        val pathResolver = PathResolver { fileRepo.read(it) }

        setContent {
            CompositionLocalProvider(
                LocalRouteContext provides routeContext,
                LocalRouteScopeFactory provides windowScope.routeScopeFactory,
                LocalImageLoader provides windowScope.imageComponent.imageLoader,
                LocalApplicationLifecycle provides applicationLifecycle,
                LocalPathResolver provides pathResolver,
                LocalRootNavigator provides rootNavigator,
            ) {
                App(
                    stackRouter = rootRouter,
                    onBackgroundColorChanged = { window.decorView.setBackgroundColor(it.toArgb()) }
                )

                NotificationPermission()
                HideSplashScreen(splashScreen)
                EdgeToEdge()
            }
        }
    }

    @Composable
    private fun NotificationPermission() {
        val launcher = rememberLauncherForActivityResult(RequestPermission()) {}

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            LaunchedEffect(Unit) {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        ContextCompat.startForegroundService(this, Intent(this, KoshService::class.java))
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
            routeContext.lifecycle.doOnStop(isOneTime = true) {
                rootRouter.reset()
            }
        } else {
            finish()
        }
    }

    private fun onCancel() {
        logger.i { "onCancel()" }
        if (moveTaskToBack(false)) {
            routeContext.lifecycle.doOnStop(isOneTime = true) {
                rootRouter.reset()
            }
        } else {
            finish()
        }
    }

    private fun navigateUp(rootRoute: RootRoute?) {
        val intent = Intent(
            this,
            KoshActivity::class.java
        ).apply {
            data = deeplink(rootRoute).toAndroidUri()
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
