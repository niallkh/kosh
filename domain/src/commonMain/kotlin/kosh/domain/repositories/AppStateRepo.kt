package kosh.domain.repositories

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.Snapshot.Companion.takeMutableSnapshot
import arrow.optics.Getter
import kosh.domain.state.AppState
import kosh.domain.utils.Copy
import kosh.domain.utils.optic
import kotlinx.coroutines.flow.StateFlow

interface AppStateRepo : Repository {

    val state: StateFlow<AppState>

    val init: StateFlow<Boolean>

    suspend fun modify(update: Copy<AppState>.() -> Unit)
}

inline fun AppStateRepo.state() = state.value

inline fun <T> AppStateRepo.optic(g: Getter<AppState, T>): StateFlow<T> = state.optic(g)

fun stateTransaction(block: () -> Unit) {
    takeMutableSnapshot().run {
        try {
            var applied = false

            while (!applied) {
                block()

                applied = apply().succeeded
            }
        } finally {
            dispose()
        }
    }
}

fun <T> MutableState<T>.compareAndSet(expect: T, update: T): Boolean {
    takeMutableSnapshot().run {
        try {
            if (value == expect) {
                value = update
            } else {
                return false
            }
            return apply().succeeded
        } finally {
            dispose()
        }
    }
}
