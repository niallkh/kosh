package kosh.app

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow

internal object BackgroundState : DefaultLifecycleObserver {

    private val logger = Logger.withTag("[K]BackgroundState")

    val background = MutableStateFlow(true)

    override fun onStart(owner: LifecycleOwner) {
        logger.v { "onStart()" }
        super.onStart(owner)
        background.value = false
        ReownConnectionManager.connect()
        ReownConnectionWorker.stop(KoshApp.appScope.context)
    }

    override fun onStop(owner: LifecycleOwner) {
        logger.v { "onStop()" }
        super.onStop(owner)
        background.value = true
        ReownConnectionWorker.start(KoshApp.appScope.context)
        ReownConnectionManager.disconnect()
    }
}
