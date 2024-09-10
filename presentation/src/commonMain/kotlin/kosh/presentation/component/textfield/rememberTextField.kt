package kosh.presentation.component.textfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import arrow.core.Either
import arrow.core.Nel
import arrow.core.left
import arrow.core.raise.recover
import arrow.core.right
import kosh.domain.failure.AppFailure
import kosh.domain.usecases.validation.Validator

@Composable
fun rememberTextField(
    initial: String? = null,
): Pair<TextFieldValue, (TextFieldValue) -> Unit> {
    var textField by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(initial ?: "", TextRange(initial?.length ?: 0)))
    }

    return Pair(textField) { textField = it }
}

@Composable
fun <O, E : AppFailure> rememberTextField(
    initial: String? = null,
    readOnly: Boolean = false,
    validator: Validator<String, O, E>,
): TextFieldState<O, E> {
    var textField by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(initial ?: "", TextRange(initial?.length ?: 0)))
    }
    var error by remember { mutableStateOf<Nel<E>?>(null) }

    return TextFieldState(
        value = textField,
        readOnly = readOnly,
        error = error,
        validate = {
            recover({
                validator(textField.text).bind().right()
            }) {
                error = it
                it.first().left()
            }
        }
    ) { value ->
        textField = value
        error = null
    }
}

@Immutable
data class TextFieldState<O, out E : AppFailure>(
    val value: TextFieldValue,
    val readOnly: Boolean,
    val error: Nel<E>?,
    val validate: () -> Either<E, O>,
    val onChange: (TextFieldValue) -> Unit,
)
