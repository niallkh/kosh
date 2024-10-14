package kosh.app

import co.touchlab.kermit.Logger
import kosh.domain.repositories.useConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

internal object ReownConnectionManager {
    private val logger = Logger.withTag("[K]ReownConnectionManager")

    private val state = MutableStateFlow(0)
    private val coroutineScope = KoshApp.appScope.coroutinesComponent.applicationScope
    private val reownRepo by lazy { KoshApp.appScope.appRepositoriesComponent.wcRepo }

    init {
        coroutineScope.launch {
            state
                .map { it > 0 }
                .debounce(300.milliseconds)
                .distinctUntilChanged()
                .collectLatest { connect ->
                    logger.v { "connect=$connect" }
                    if (connect) {
                        reownRepo.useConnection()
                    }
                }
        }
    }

    fun connect() {
        state.update { it + 1 }
    }

    fun disconnect() {
        state.update { it - 1 }
    }
}
