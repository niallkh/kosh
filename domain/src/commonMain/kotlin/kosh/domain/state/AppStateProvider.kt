package kosh.domain.state

import androidx.compose.runtime.Stable
import kosh.domain.repositories.AppStateRepo
import kotlinx.coroutines.flow.StateFlow

@Stable
interface AppStateProvider : StateFlow<AppState> {
    val init: StateFlow<Boolean>
}

class DefaultAppStateProvider(
    private val appStateRepo: AppStateRepo,
) : AppStateProvider, StateFlow<AppState> by appStateRepo.state {

    override val init: StateFlow<Boolean>
        get() = appStateRepo.init
}
