package kosh.app.di

import kosh.app.IosPushNotifier

public interface IosAppComponent : AppComponent {
    public val iosPushNotifier: IosPushNotifier
}
