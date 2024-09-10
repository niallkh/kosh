package kosh.data

import arrow.atomic.AtomicBoolean
import kosh.data.sources.AppStateSource
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.suspendLazy
import kosh.domain.state.AppState
import kosh.domain.utils.selectorLatest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DefaultAppStateRepo(
    private val appStateSource: AppStateSource,
    private val applicationScope: CoroutineScope,
) : AppStateRepo, CoroutineScope by applicationScope {

    private val init = AtomicBoolean(false)

    private val initMemo = suspendLazy {
        state.update { appStateSource.state.first() }
        init.set(true)
    }

    override val state = MutableStateFlow(AppState.Default)

    init {
        applicationScope.launch {
            init()
            state.selectorLatest { newState ->
                appStateSource.update { newState }
            }
        }
    }

    override suspend fun init() = initMemo()

    override fun compareAndSet(expect: AppState, update: AppState): Boolean {
        require(init.value) { "AppState not initialized" }
        return state.compareAndSet(expect, update)
    }
}
