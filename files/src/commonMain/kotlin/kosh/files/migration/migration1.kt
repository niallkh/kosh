package kosh.files.migration

import kosh.data.migration.StateMigration
import kosh.domain.state.AppState
import kosh.domain.utils.copy

fun migration1(
    debug: Boolean,
): StateMigration<AppState> = appStateMigration(1) {
    copy {
        chains()

        if (debug) {
            sepolia()
        }
    }
}

internal fun appStateMigration(
    version: Int,
    migration: AppState.() -> AppState,
) = object : StateMigration<AppState> {
    override suspend fun cleanUp() {
    }

    override suspend fun shouldMigrate(currentData: AppState): Boolean {
        return currentData.version < version
    }

    override suspend fun migrate(currentData: AppState): AppState {
        return currentData.migration().copy(version = version)
    }
}
