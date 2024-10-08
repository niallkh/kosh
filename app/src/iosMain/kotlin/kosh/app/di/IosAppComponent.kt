package kosh.app.di

import kosh.domain.core.provider
import platform.Foundation.NSUserDefaults

class IosAppComponent : AppComponent {
    override val debug: Boolean by provider {
        NSUserDefaults.standardUserDefaults.boolForKey("isDebug")
    }
}
