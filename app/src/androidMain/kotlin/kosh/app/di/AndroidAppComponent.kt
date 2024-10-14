package kosh.app.di

import android.content.Context
import kosh.app.AndroidPushNotifier

public interface AndroidAppComponent : AppComponent {
    public val context: Context
    public val androidPushNotifier: AndroidPushNotifier
}
