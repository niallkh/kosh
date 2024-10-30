package kosh.app.di

import kosh.domain.core.provider
import platform.Foundation.NSBundle

internal class IosAppComponent : AppComponent {

    override val debug: Boolean by provider {
        getBundleValue("DEBUG") == "true"
    }

    override val reownProject: String by provider {
        getBundleValue("REOWN_PROJECT")
    }

    override val groveKey: String by provider {
        getBundleValue("GROVE_KEY")
    }

    private fun getBundleValue(
        key: String,
    ) = (NSBundle.mainBundle.objectForInfoDictionaryKey(key) as? String
        ?: error("$key not provided"))
}
