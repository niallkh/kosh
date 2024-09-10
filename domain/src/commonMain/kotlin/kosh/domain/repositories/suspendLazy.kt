package kosh.domain.repositories

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


val NULL = Any()

fun <T> suspendLazy(factory: suspend () -> T): suspend () -> T {
    val mutex = Mutex()
    var value: Any? = NULL
    var localFactory: (suspend () -> T)? = factory

    return suspend {
        if (value !== NULL) {
            @Suppress("UNCHECKED_CAST")
            value as T
        } else {
            mutex.withLock {
                if (value !== NULL) {
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
