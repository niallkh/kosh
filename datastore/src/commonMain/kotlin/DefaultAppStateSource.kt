package kosh.datastore

import kosh.data.sources.AppStateSource
import kosh.domain.state.AppState
import kotlinx.coroutines.flow.Flow

class DefaultAppStateSource(
    private val dataStore: AppStateDataStore,
) : AppStateSource {

    override val state: Flow<AppState>
        get() = dataStore.data

    override suspend fun update(update: (AppState) -> AppState): AppState =
        dataStore.updateData(update)
}
