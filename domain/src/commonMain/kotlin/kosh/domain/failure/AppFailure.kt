package kosh.domain.failure

import androidx.compose.runtime.Immutable
import co.touchlab.kermit.Logger

@Immutable
interface AppFailure {
    val message: String
}

class AppFailureException(val failure: AppFailure) : Exception(failure.message)

fun AppFailure.throwFailure(): Nothing {
    throw AppFailureException(this)
}

fun Logger.logFailure(app: AppFailure) {
    w { "App Failure: ${app.message}" }
}
