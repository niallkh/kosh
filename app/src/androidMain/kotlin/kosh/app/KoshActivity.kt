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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import co.touchlab.kermit.Logger
import com.seiko.imageloader.LocalImageLoader
import kosh.app.di.androidPresentationContext
import kosh.presentation.core.LocalPresentationContext
import kosh.presentation.core.PresentationContext
import kosh.ui.navigation.deeplink
import kosh.ui.navigation.parseDeeplink
import kosh.ui.navigation.routes.RootRoute

public class KoshActivity : FragmentActivity() {

    private val logger = Logger.withTag("[K]KoshActivity")

    private lateinit var presentationContext: PresentationContext

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        logger.d { "onCreate()" }

        val initialLink = intent?.data?.toString()?.let(::parseDeeplink)

        logger.v { "action=${intent.action}, deeplink=${intent.data}" }

        presentationContext = androidPresentationContext(
            applicationScope = KoshApp.appScope,
            activity = this,
        )

        setContent {
            CompositionLocalProvider(
                LocalPresentationContext provides presentationContext,
                LocalImageLoader provides KoshApp.appScope.imageComponent.imageLoader,
            ) {
                App(
                    initialLink = initialLink,
                    onResult = { onResult(it.redirect) }
                )

                Permissions()
            }
        }
    }

    @Composable
    private fun Permissions() {
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            logger.v { "Permissions() = $it" }
        }

        LaunchedEffect(Unit) {
            val permissions = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS,
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT,
                    )

                    else -> arrayOf(
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT,
                    )
                }

                else -> arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            launcher.launch(permissions)
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
            presentationContext.presentationScope.deeplinkHandler.handle(it)
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
            presentationContext.presentationScope.deeplinkHandler.handle(null)
        } else {
            if (!moveTaskToBack(false)) {
                finish()
            } else {
                presentationContext.presentationScope.deeplinkHandler.handle(null)
            }
        }
    }

    override fun onDestroy() {
        logger.d { "onDestroy()" }
        super.onDestroy()
    }
}
