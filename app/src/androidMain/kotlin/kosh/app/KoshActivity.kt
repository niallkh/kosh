package kosh.app

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import co.touchlab.kermit.Logger
import com.seiko.imageloader.LocalImageLoader
import kosh.app.di.androidUiContext
import kosh.presentation.core.LocalUiContext
import kosh.presentation.core.UiContext
import kosh.ui.component.path.LocalPathResolver
import kosh.ui.component.path.PathResolver
import kosh.ui.navigation.deeplink
import kosh.ui.navigation.parseDeeplink
import kosh.ui.navigation.routes.RootRoute

public class KoshActivity : FragmentActivity() {

    private val logger = Logger.withTag("[K]KoshActivity")

    private lateinit var uiContext: UiContext

    private var link by mutableStateOf<RootRoute?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        logger.d { "onCreate()" }

        link = intent?.data?.toString()?.let(::parseDeeplink)

        logger.v { "action=${intent.action}, deeplink=${intent.data}" }

        uiContext = androidUiContext(
            applicationScope = KoshApp.appScope,
            activity = this,
        )

        val pathResolver = PathResolver {
            uiContext.uiScope.appRepositoriesComponent.fileRepo.read(it)
        }

        setContent {
            CompositionLocalProvider(
                LocalUiContext provides uiContext,
                LocalImageLoader provides KoshApp.appScope.imageComponent.imageLoader,
                LocalPathResolver provides pathResolver,
            ) {
                App(
                    link = link,
                    onResult = { onResult(it.redirect) }
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
            link = parseDeeplink(it)
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
