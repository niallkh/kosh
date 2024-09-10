package kosh.app.di

import kosh.app.BuildConfig

class AndroidAppComponent : AppComponent {
    override val debug: Boolean
        get() = BuildConfig.DEBUG
}
