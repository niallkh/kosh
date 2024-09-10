package kosh.ui.ledger

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import kosh.domain.repositories.LedgerListener
import kosh.ui.component.scaffold.LocalSnackbarHostState
import kotlin.coroutines.cancellation.CancellationException

@Composable
fun LedgerButtonRequest(
    request: LedgerListener.ButtonRequest?,
    onDismiss: (LedgerListener.ButtonRequest) -> Unit,
    onConfirm: (LedgerListener.ButtonRequest) -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val hapticFeedback = LocalHapticFeedback.current

    val updatedDismiss = rememberUpdatedState(onDismiss)
    val updatedConfirm = rememberUpdatedState(onConfirm)

    LaunchedEffect(request) {
        request?.let { buttonRequest ->
            try {
                snackbarHostState.currentSnackbarData?.dismiss()
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)

                val result = snackbarHostState.showSnackbar(
                    message = buttonRequest.name,
                    actionLabel = "OK",
                    duration = SnackbarDuration.Indefinite,
                    withDismissAction = true,
                )

                when (result) {
                    SnackbarResult.Dismissed -> updatedDismiss.value(buttonRequest)
                    SnackbarResult.ActionPerformed -> updatedConfirm.value(buttonRequest)
                }
            } catch (e: CancellationException) {
                updatedDismiss.value(buttonRequest)
            }
        }
    }
}
