package kosh.presentation.core

import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.Job

inline fun Lifecycle.doWhileStarted(crossinline block: () -> Job) {
    lateinit var job: Job
    subscribe(
        object : Lifecycle.Callbacks {
            override fun onStart() {
                job = block()
            }

            override fun onStop() {
                job.cancel()
            }
        }
    )
}
