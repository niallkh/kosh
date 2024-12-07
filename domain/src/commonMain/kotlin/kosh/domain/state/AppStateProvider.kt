package kosh.domain.state

import androidx.compose.runtime.Stable
import kosh.domain.repositories.AppStateRepo
import kotlinx.coroutines.flow.StateFlow

@Stable
interface AppStateProvider : StateFlow<AppState> {
    val state: AppState
    val init: Boolean
}

class DefaultAppStateProvider(
    private val appStateRepo: AppStateRepo,
) : AppStateProvider, StateFlow<AppState> by appStateRepo.stateFlow {

    override val state: AppState
        get() = appStateRepo.state

    override val init: Boolean
        get() = appStateRepo.init
}
