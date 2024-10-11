package kosh.data

import arrow.atomic.AtomicBoolean
import kosh.data.sources.AppStateSource
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.suspendLazy
import kosh.domain.state.AppState
import kosh.domain.utils.selectLatest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DefaultAppStateRepo(
    private val appStateSource: AppStateSource,
    private val applicationScope: CoroutineScope,
) : AppStateRepo, CoroutineScope by applicationScope {

    private val init = AtomicBoolean(false)

    override val state = MutableStateFlow(AppState.Default)

    private val initLazy = suspendLazy {
        state.value = appStateSource.state.first()
        init.set(true)
    }

    init {
        applicationScope.launch {
            init()
            state.selectLatest { newState ->
                appStateSource.update { newState }
            }
        }
    }

    override suspend fun init() = initLazy()

    override fun compareAndSet(expect: AppState, update: AppState): Boolean {
        require(init.value) { "AppState not initialized" }
        return state.compareAndSet(expect, update)
    }
}
