package kosh.app.di.impl

import kosh.app.di.AppComponent
import kosh.domain.core.provider
import platform.Foundation.NSUserDefaults

class IosAppComponent : AppComponent {
    override val debug: Boolean by provider {
        NSUserDefaults.standardUserDefaults.boolForKey("isDebug")
    }
}
