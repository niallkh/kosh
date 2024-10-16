package kosh.ui.component.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import kosh.ui.component.modifier.optionalClickable
import kosh.ui.navigation.animation.transitions.fadeIn
import kosh.ui.navigation.animation.transitions.fadeOut

@Composable
fun KoshTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    error: String? = null,
    requestFocus: Boolean = false,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    label: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val focusRequester = remember { FocusRequester() }
    val hapticFeedback = LocalHapticFeedback.current

    OutlinedTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = value,
        onValueChange = onValueChange,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        isError = error != null,
        readOnly = readOnly,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        label = label,
        supportingText = {
            val transition = updateTransition(error)

            transition.AnimatedVisibility(
                visible = { it != null },
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Text(transition.targetState ?: transition.currentState ?: "")
            }
        },
        visualTransformation = visualTransformation
    )

    LaunchedEffect(requestFocus) {
        if (requestFocus) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(error) {
        if (error != null) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }
}

@Composable
fun PasteClearIcon(
    textField: TextFieldValue,
    onChange: (TextFieldValue) -> Unit,
) {
    if (textField.text.isEmpty()) {
        val clipboardManager = LocalClipboardManager.current

        IconButton({
            clipboardManager.getText()?.let {
                onChange(
                    textField.copy(
                        text = it.text,
                        selection = TextRange(it.text.length)
                    )
                )
            }
        }) {
            Icon(Icons.Default.ContentPaste, contentDescription = "Paste")
        }
    } else {
        IconButton({
            onChange(textField.copy(text = "", selection = TextRange.Zero))
        }) {
            Icon(Icons.Outlined.Cancel, contentDescription = "Clear")
        }
    }
}

@Composable
fun KoshTextFieldReadOnly(
    modifier: Modifier = Modifier,
    value: String,
    textStyle: TextStyle = LocalTextStyle.current,
    singleLine: Boolean = true,
    onClick: (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        modifier = modifier.optionalClickable(onClick),
        value = value,
        onValueChange = {},
        singleLine = singleLine,
        placeholder = placeholder,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        supportingText = supportingText,
        label = label,
        readOnly = true,
        textStyle = textStyle
    )
}
