package kosh.ui.failure

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import kosh.domain.failure.AppFailure
import kosh.domain.serializers.Nel
import kosh.ui.component.scaffold.LocalSnackbarHostState

@Composable
fun <T : AppFailure> AppFailureMessage(
    failure: T?,
) {
    AppFailureMessage(failure, { false }, {})
}

@Composable
fun <T : AppFailure> AppFailureMessage(
    failure: T?,
    isRetryAble: (T) -> Boolean = { true },
    retry: () -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current

    val currentRetry = rememberUpdatedState(retry)

    LaunchedEffect(failure) {
        failure?.let { failure ->
            snackbarHostState.currentSnackbarData?.dismiss()

            val retryAble = isRetryAble(failure)

            val result = snackbarHostState.showSnackbar(
                message = failure.message,
                actionLabel = "Retry".takeIf { retryAble },
                duration = if (retryAble) SnackbarDuration.Indefinite else SnackbarDuration.Long,
                withDismissAction = true,
            )

            when (result) {
                SnackbarResult.ActionPerformed -> currentRetry.value()
                SnackbarResult.Dismissed -> Unit
            }
        }
    }
}

@Composable
fun <T : AppFailure> AppFailureMessage(failures: Nel<T>?) {
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(failures) {
        snackbarHostState.currentSnackbarData?.dismiss()

        failures?.forEach { failure ->
            snackbarHostState.showSnackbar(
                message = failure.message,
                duration = SnackbarDuration.Short,
                withDismissAction = true,
            )
        }
    }
}
