package kosh.data.sources

import kosh.domain.state.AppState
import kotlinx.coroutines.flow.Flow

interface AppStateSource {

    val state: Flow<AppState>

    suspend fun update(update: (AppState) -> AppState): AppState
}
