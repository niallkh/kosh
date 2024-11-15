package kosh.presentation.component.textfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun rememberTextField(
    initial: String? = null,
): TextFieldState {
    var textField by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(initial ?: "", TextRange(initial?.length ?: 0)))
    }

    return remember {
        object : TextFieldState {
            override val value: TextFieldValue get() = textField
            override fun onChange(value: TextFieldValue) {
                textField = value
            }
        }
    }
}

@Stable
interface TextFieldState {
    val value: TextFieldValue
    fun onChange(value: TextFieldValue)
}
