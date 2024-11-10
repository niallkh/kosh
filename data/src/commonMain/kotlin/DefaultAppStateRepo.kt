package kosh.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotApplyConflictException
import kosh.data.migration.StateMigration
import kosh.data.sources.KeyValueStore
import kosh.domain.repositories.AppStateRepo
import kosh.domain.state.AppState
import kosh.domain.utils.Copy
import kosh.domain.utils.copy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class DefaultAppStateRepo(
    private val keyValueStore: KeyValueStore,
    private val applicationScope: CoroutineScope,
    private val migrations: List<StateMigration<AppState>>,
) : AppStateRepo, CoroutineScope by applicationScope {

    override var state by mutableStateOf(AppState.Default())

    override val stateFlow: StateFlow<AppState> = snapshotFlow { state }
        .stateIn(applicationScope, SharingStarted.Eagerly, state)

    override var init by mutableStateOf(false)

    init {
        applicationScope.launch {
            val saved = keyValueStore.get(AppState.key, AppState.serializer()) ?: state
            val migrated = migrate(saved, migrations) ?: saved

            Snapshot.global {
                state = migrated
                init = true
            }

            stateFlow
                .sample(300.milliseconds)
                .select(state) {
                    keyValueStore.put(AppState.key, it, AppState.serializer())
                }
        }
    }

    override suspend fun update(update: Copy<AppState>.() -> Unit) {
        if (!init) {
            snapshotFlow { init }.first { it }
        }

        while (true) {
            val snapshot = Snapshot.takeMutableSnapshot()
            try {
                snapshot.enter { state = state.copy(update) }
                snapshot.apply().check()
            } catch (e: SnapshotApplyConflictException) {
                continue
            } finally {
                snapshot.dispose()
            }
            break
        }
    }

    private suspend fun migrate(
        initial: AppState,
        migrations: List<StateMigration<AppState>>,
    ): AppState? {
        val filtered = migrations.filter { it.shouldMigrate(initial) }

        val migrated = filtered.fold(initial) { state, migration -> migration.migrate(state) }

        filtered.forEach { it.cleanUp() }

        return migrated.takeIf { it != initial }
    }

}

private suspend fun <T> Flow<T>.select(
    initial: T,
    block: suspend (T) -> Unit,
) {
    var last = initial
    collectLatest {
        if (it != last) {
            last = it
            block(it)
        } else {
            last = it
        }
    }
}
