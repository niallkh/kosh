package kosh.domain.repositories

import androidx.compose.runtime.Stable
import kosh.domain.state.AppState
import kosh.domain.utils.Copy
import kotlinx.coroutines.flow.StateFlow

@Stable
interface AppStateRepo : Repository {

    val state: AppState

    @Deprecated("")
    val stateFlow: StateFlow<AppState>

    val init: Boolean

    suspend fun update(update: Copy<AppState>.() -> Unit)
}

