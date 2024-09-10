package kosh.domain.repositories

import arrow.optics.Getter
import kosh.domain.state.AppState
import kosh.domain.utils.Copy
import kosh.domain.utils.copy
import kosh.domain.utils.optic
import kotlinx.coroutines.flow.StateFlow

interface AppStateRepo : Repository {

    val state: StateFlow<AppState>

    suspend fun init()

    fun compareAndSet(expect: AppState, update: AppState): Boolean
}

inline fun AppStateRepo.state() = state.value

inline fun <T> AppStateRepo.optic(g: Getter<AppState, T>): StateFlow<T> = state.optic(g)

inline fun AppStateRepo.modify(update: Copy<AppState>.() -> Unit) {
    while (true) {
        val prevValue = state.value
        val nextValue = prevValue.copy { update() }
        if (compareAndSet(prevValue, nextValue)) {
            return
        }
    }
}
