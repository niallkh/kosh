package kosh.app.di

import kosh.domain.core.provider
import platform.Foundation.NSBundle

internal class IosAppComponent : AppComponent {
    override val debug: Boolean by provider {
        NSBundle.mainBundle.objectForInfoDictionaryKey("DEBUG") == "true"
    }
}
