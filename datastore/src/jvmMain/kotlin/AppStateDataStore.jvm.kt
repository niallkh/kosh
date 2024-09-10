package kosh.datastore

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import kosh.domain.state.AppState

actual fun corruptionHandler(): ReplaceFileCorruptionHandler<AppState> {
    TODO("Not yet implemented")
}
