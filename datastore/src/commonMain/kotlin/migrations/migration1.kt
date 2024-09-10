package kosh.datastore.migrations

import androidx.datastore.core.DataMigration
import kosh.datastore.appStateMigration
import kosh.domain.state.AppState
import kosh.domain.utils.copy

internal fun migration1(
    debug: Boolean,
): DataMigration<AppState> = appStateMigration(1) {
    copy {
        chains()

        if (debug) {
            sepolia()
        }
    }
}
