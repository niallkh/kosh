package kosh.domain.state

import androidx.compose.runtime.Stable
import kosh.domain.repositories.AppStateRepo
import kotlinx.coroutines.flow.StateFlow

@Stable
interface AppStateProvider : StateFlow<AppState>

class DefaultAppStateProvider(
    appStateRepo: AppStateRepo,
) : AppStateProvider, StateFlow<AppState> by appStateRepo.state
