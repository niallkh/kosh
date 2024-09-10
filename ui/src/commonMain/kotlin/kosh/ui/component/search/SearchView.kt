package kosh.ui.component.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    placeholder: String,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (TextFieldValue) -> Unit,
    onUp: () -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val focusRequester = remember { FocusRequester() }

    TextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .height(72.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = keyboardActions,
        trailingIcon = trailingIcon,
        leadingIcon = {
            IconButton(
                modifier = Modifier.size(56.dp),
                onClick = onUp,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Navigation back"
                )
            }
        },
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
        ),
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
