package kosh.app

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import kosh.app.di.AndroidAppScope


internal class KoshApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Logger.setTag("[K]")

        if (BuildConfig.DEBUG) {
//            StrictMode.enableDefaults()
            Logger.setMinSeverity(Severity.Verbose)
        } else {
            Logger.setMinSeverity(Severity.Info)
            Thread.setDefaultUncaughtExceptionHandler { t, e ->
                Logger.e(e) { "Error happened" }
            }
        }

        appScope = AndroidAppScope(this)
        appScope.androidPushNotifier.start()

        ProcessLifecycleOwner.get().lifecycle.addObserver(BackgroundState)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var appScope: AndroidAppScope
    }
}
