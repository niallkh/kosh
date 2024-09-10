package kosh.ui.failure

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import kosh.domain.failure.AppFailure
import kosh.domain.serializers.Nel
import kosh.domain.utils.cancellation
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

    val updatedRetry = rememberUpdatedState(retry)

    LaunchedEffect(failure) {
        failure?.let { failure ->
            snackbarHostState.currentSnackbarData?.dismiss()

            val retryAble = isRetryAble(failure)

            val result = snackbarHostState.showSnackbar(
                message = failure.message,
                actionLabel = "retry".takeIf { retryAble },
                duration = if (retryAble) SnackbarDuration.Indefinite else SnackbarDuration.Long,
                withDismissAction = retryAble,
            )

            when (result) {
                SnackbarResult.ActionPerformed -> updatedRetry.value()
                SnackbarResult.Dismissed -> Unit
            }
        }
    }
}

@Composable
fun <T : AppFailure> AppFailureMessage(
    failures: Nel<T>?,
    isRetryAble: (T) -> Boolean = { true },
    retry: () -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current

    val updatedRetry = rememberUpdatedState(retry)

    LaunchedEffect(failures) {
        failures?.forEach { failure ->
            snackbarHostState.currentSnackbarData?.dismiss()

            val retryAble = isRetryAble(failure)

            val result = snackbarHostState.showSnackbar(
                message = failure.message,
                actionLabel = "retry".takeIf { retryAble },
                duration = if (retryAble) SnackbarDuration.Long else SnackbarDuration.Short,
                withDismissAction = retryAble,
            )

            when (result) {
                SnackbarResult.ActionPerformed -> {
                    updatedRetry.value()
                    cancellation()
                }

                SnackbarResult.Dismissed -> Unit
            }
        }
    }
}
