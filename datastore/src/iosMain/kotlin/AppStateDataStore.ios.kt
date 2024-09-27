package kosh.datastore

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import co.touchlab.kermit.Logger
import kosh.domain.state.AppState

actual fun corruptionHandler() = ReplaceFileCorruptionHandler {
    Logger.e(it) { "App state file corrupted" }
    AppState.Default
}
