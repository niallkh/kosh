package kosh.data

import kosh.data.sources.AppStateSource
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.suspendLazy
import kosh.domain.state.AppState
import kosh.domain.utils.Copy
import kosh.domain.utils.copy
import kosh.domain.utils.selectLatest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DefaultAppStateRepo(
    private val appStateSource: AppStateSource,
    private val applicationScope: CoroutineScope,
) : AppStateRepo, CoroutineScope by applicationScope {

    override val state = MutableStateFlow(AppState.Default)
    override val init = MutableStateFlow(false)

    private val initLazy = suspendLazy {
        state.value = appStateSource.state.first()
        init.value = true
    }

    init {
        applicationScope.launch {
            initLazy()
            state.selectLatest { newState ->
                appStateSource.update { newState }
            }
        }
    }

    override suspend fun modify(update: Copy<AppState>.() -> Unit) {
        if (!init.value) {
            init.first { it }
        }

        state.update { it.copy(update) }
    }
}
