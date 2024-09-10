package kosh.ui.failure

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import kosh.domain.failure.AppFailure
import kosh.domain.serializers.Either
import kosh.ui.resources.Res
import kosh.ui.resources.failure_dialog_cancel_btn
import kosh.ui.resources.failure_dialog_ok_btn
import org.jetbrains.compose.resources.stringResource

@Composable
fun <F : AppFailure> AppFailureAlert(
    modifier: Modifier = Modifier,
    failure: F?,
    onDismiss: (F) -> Unit,
    onCancel: ((F) -> Unit)? = null,
    onConfirm: (F) -> Unit = onDismiss,
    confirm: @Composable (F) -> String? = { null },
    cancel: @Composable (F) -> String? = { null },
    text: @Composable (F) -> String? = { null },
) {
    if (failure != null) {
        val dismissButton = @Composable {
            if (onCancel != null) {
                TextButton(onClick = { onCancel(failure) }) {
                    Text(cancel(failure) ?: stringResource(Res.string.failure_dialog_cancel_btn))
                }
            }
        }

        AlertDialog(
            modifier = modifier,
            onDismissRequest = { onDismiss(failure) },
            confirmButton = {
                TextButton(onClick = { onConfirm(failure) }) {
                    Text(confirm(failure) ?: stringResource(Res.string.failure_dialog_ok_btn))
                }
            },
            dismissButton = dismissButton.takeIf { onCancel != null },
            text = { Text(text(failure) ?: failure.message) },
        )

        val hapticFeedback = LocalHapticFeedback.current
        LaunchedEffect(Unit) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }
}

@Composable
fun <F1 : AppFailure, F2 : AppFailure> AppFailureAlert(
    modifier: Modifier = Modifier,
    failure: Either<F1, F2>?,
    onDismiss: (Either<F1, F2>) -> Unit,
    onCancel: (Either<F1, F2>) -> Unit = onDismiss,
    onConfirm: (Either<F1, F2>) -> Unit = onDismiss,
) {
    if (failure != null) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = { onDismiss(failure) },
            confirmButton = {
                TextButton(onClick = { onConfirm(failure) }) {
                    Text(stringResource(Res.string.failure_dialog_ok_btn))
                }
            },
            dismissButton = {
                TextButton(onClick = { onCancel(failure) }) {
                    Text(stringResource(Res.string.failure_dialog_cancel_btn))
                }
            },
            text = {
                Text(failure.fold(
                    { it.message },
                    { it.message }
                ))
            },
        )

        val hapticFeedback = LocalHapticFeedback.current
        LaunchedEffect(Unit) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }
}
