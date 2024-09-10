package kosh.domain.failure

import androidx.compose.runtime.Immutable
import co.touchlab.kermit.Logger

@Immutable
interface AppFailure {
    val message: String
}

fun Logger.logFailure(app: AppFailure) {
    w { "App Failure: ${app.message}" }
}
