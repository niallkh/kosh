package kosh.app.di

import android.content.pm.PackageManager
import kosh.app.BuildConfig
import kosh.domain.core.provider

internal class AndroidAppComponent(
    androidComponent: AndroidComponent,
) : AppComponent, AndroidComponent by androidComponent {

    override val debug: Boolean
        get() = BuildConfig.DEBUG

    private val appMetadata by provider {
        context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            .metaData
    }

    override val bugsnagKey: String by provider {
        getMetadataValue("com.bugsnag.android.API_KEY")
    }

    override val reownProject: String by provider {
        getMetadataValue("eth.kosh.REOWN_PROJECT")
    }

    override val groveKey: String by provider {
        getMetadataValue("eth.kosh.GROVE_KEY")
    }

    private fun getMetadataValue(key: String): String =
        appMetadata?.getString(key) ?: error("$key not provided")
}
