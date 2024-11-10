package kosh.domain.utils

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

val NIL = Any()

fun <T> suspendLazy(factory: suspend () -> T): suspend () -> T {
    val mutex = Mutex()
    var value: Any? = NIL
    var localFactory: (suspend () -> T)? = factory

    return suspend {
        if (value !== NIL) {
            @Suppress("UNCHECKED_CAST")
            value as T
        } else {
            mutex.withLock {
                if (value !== NIL) {
                    @Suppress("UNCHECKED_CAST")
                    value as T
                } else {
                    localFactory!!().also {
                        value = it
                        localFactory = null
                    }
                }
            }
        }
    }
}
