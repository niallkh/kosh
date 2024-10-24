package kosh.app

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import kosh.app.di.AndroidAppScope


internal class KoshApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(BackgroundState)
        appScope = AndroidAppScope(this)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var appScope: AndroidAppScope
    }
}
