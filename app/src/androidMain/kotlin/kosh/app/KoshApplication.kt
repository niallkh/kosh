package kosh.app

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import arrow.atomic.AtomicInt
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import kosh.app.di.AndroidApplicationScope
import kotlinx.coroutines.flow.MutableStateFlow


class KoshApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Logger.setTag("[K]")

        if (BuildConfig.DEBUG) {
//            StrictMode.enableDefaults()
            Logger.setMinSeverity(Severity.Verbose)
        } else {
            Logger.setMinSeverity(Severity.Debug)
            Thread.setDefaultUncaughtExceptionHandler { t, e ->
                Logger.e(e) { "Error happened" }
            }
        }

        registerActivityLifecycleCallbacks(ActivityCallbacks)

        applicationScope = AndroidApplicationScope(this)
        applicationScope.androidPushNotifier.start()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var applicationScope: AndroidApplicationScope
    }
}

object ActivityCallbacks : Application.ActivityLifecycleCallbacks {

    private val startedActivities = AtomicInt()
    private val createdActivities = AtomicInt()
    val background = MutableStateFlow(true)

    private val logger = Logger.withTag("[K]Activities")

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (createdActivities.getAndIncrement() == 0) {
            background.value = false
        }
        logger.d { "onActivityCreated(${createdActivities.get()})" }
    }

    override fun onActivityStarted(activity: Activity) {
        if (startedActivities.getAndIncrement() == 0) {
            background.value = false
        }
        logger.d { "onActivityStarted(${startedActivities.get()})" }
    }

    override fun onActivityResumed(activity: Activity) {
        logger.d { "onActivityResumed()" }
    }

    override fun onActivityPaused(activity: Activity) {
        logger.d { "onActivityPaused()" }
    }

    override fun onActivityStopped(activity: Activity) {
        if (startedActivities.decrementAndGet() == 0) {
            background.value = true
        }
        logger.d { "onActivityStopped(${startedActivities.get()})" }
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (createdActivities.decrementAndGet() == 0) {
            background.value = true
        }
        logger.d { "onActivityDestroyed(${createdActivities.get()})" }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        logger.v { "onActivitySaveInstanceState()" }
    }
}
