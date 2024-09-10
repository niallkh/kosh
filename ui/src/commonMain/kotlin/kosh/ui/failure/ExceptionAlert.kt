package kosh.ui.failure

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kosh.domain.serializers.ImmutableList
import kosh.ui.component.scaffold.LocalSnackbarHostState

@Composable
fun ExceptionAlert(
    exceptions: ImmutableList<Throwable>,
    onDismiss: (Throwable) -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current

    val exception = exceptions.lastOrNull()

    if (exception != null) {
        LaunchedEffect(exception) {
            try {
                snackbarHostState.currentSnackbarData?.dismiss()

                snackbarHostState.showSnackbar(
                    exception.message?.take(512) ?: "Exception happened"
                )
            } finally {
                onDismiss(exception)
            }
        }
    }
}
