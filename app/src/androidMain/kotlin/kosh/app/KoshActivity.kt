package kosh.app

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.seiko.imageloader.LocalImageLoader
import kosh.app.di.androidUiContext
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
import kosh.ui.navigation.stack.StackRouter
import kotlinx.serialization.serializer

public class KoshActivity : FragmentActivity() {

    private val logger = Logger.withTag("[K]KoshActivity")

    private lateinit var uiContext: UiContext
    private lateinit var rootRouter: StackRouter<RootRoute>

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        logger.d { "onCreate()" }

        val appStateProvider = KoshApp.appScope.domain.appStateProvider
        splashScreen.setKeepOnScreenCondition { !appStateProvider.init.value }

        val deeplink = intent?.data?.toString()?.let(::parseDeeplink)

        logger.v { "action=${intent.action}, deeplink=${intent.data}" }

        uiContext = androidUiContext(
            applicationScope = KoshApp.appScope,
            activity = this,
        )

        val start = RootRoute.Home()
        val rootRouter = DefaultStackRouter(
            uiContext = uiContext,
            serializer = serializer(),
            start = start,
            link = deeplink,
            onResult = {
                when (it) {
                    is RouteResult.Result -> onResult(it.redirect)
                    is RouteResult.Up -> when (val route = it.route) {
                        null -> replaceAll(start)
                        else -> replaceAll(start, route)
                    }
                }
            },
        )
        this.rootRouter = rootRouter

        val rootNavigator = RootNavigator { rootRouter.pushNew(it) }
        val pathResolver = PathResolver {
            uiContext.uiScope.appRepositoriesComponent.fileRepo.read(it)
        }
        uiContext.uiScope.deeplinkHandler.subscribe {
            it?.let(::parseDeeplink).let(rootRouter::handle)
        }

        setContent {
            CompositionLocalProvider(
                LocalUiContext provides uiContext,
                LocalImageLoader provides KoshApp.appScope.imageComponent.imageLoader,
                LocalPathResolver provides pathResolver,
                LocalRootNavigator provides rootNavigator,
            ) {
                App(
                    stackRouter = rootRouter,
                )

                NotificationPermission()
                BluetoothPermission()
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        enableEdgeToEdge()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        logger.d { "onNewIntent()" }
        logger.v { "action=${intent.action}, deeplink=${intent.data}" }

        intent.data?.toString()?.let {
            uiContext.uiScope.deeplinkHandler.handle(it)
        }
    }

    private fun onResult(redirect: String?) {
        logger.i { "onResult(redirect=$redirect)" }

        if (!redirect.isNullOrEmpty()) {
            startActivity(Intent(Intent.ACTION_VIEW, redirect.toUri()))
        }

        finish()
    }

    override fun onDestroy() {
        logger.d { "onDestroy()" }
        super.onDestroy()
    }
}
