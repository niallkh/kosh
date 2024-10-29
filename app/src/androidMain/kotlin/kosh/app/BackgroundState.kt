package kosh.app

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow

internal object BackgroundState : DefaultLifecycleObserver {

    private val logger = Logger.withTag("[K]BackgroundState")

    private val background = MutableStateFlow(true)

    override fun onStart(owner: LifecycleOwner) {
        logger.v { "onStart()" }
        super.onStart(owner)
        ReownConnectionManager.connect()
        ReownConnectionWorker.stop(KoshApp.appScope.context)
    }

    override fun onStop(owner: LifecycleOwner) {
        logger.v { "onStop()" }
        super.onStop(owner)
        ReownConnectionWorker.start(KoshApp.appScope.context)
        ReownConnectionManager.disconnect()
    }
}
