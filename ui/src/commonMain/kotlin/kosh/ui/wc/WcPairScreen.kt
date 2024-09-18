package kosh.ui.wc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import arrow.core.toEitherNel
import kosh.domain.failure.WcFailure
import kosh.domain.models.wc.PairingUri
import kosh.domain.models.wc.WcAuthentication
import kosh.domain.models.wc.WcProposal
import kosh.presentation.component.textfield.TextFieldState
import kosh.presentation.component.textfield.rememberTextField
import kosh.presentation.wc.PairState
import kosh.presentation.wc.rememberPair
import kosh.ui.component.button.LoadingButton
import kosh.ui.component.button.PrimaryButtons
import kosh.ui.component.nullUnless
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.single.single
import kosh.ui.component.textfield.KoshTextField
import kosh.ui.component.textfield.PasteClearIcon
import kosh.ui.failure.AppFailureMessage
import kosh.ui.resources.Res
import kosh.ui.resources.wc_pair_title
import kosh.ui.resources.wc_pair_uri_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun WcPairScreen(
    modifier: Modifier = Modifier,
    initial: PairingUri?,
    onCancel: () -> Unit,
    onNavigateUp: () -> Unit,
    onProposal: (WcProposal) -> Unit,
    onAuthenticate: (WcAuthentication) -> Unit,
) {
    val pair = rememberPair(initial)

    val textField = rememberTextField(initial?.value ?: "", initial != null) {
        PairingUri(it).toEitherNel()
    }

    AppFailureMessage(pair.failure) {
        pair.retry()
    }

    LaunchedEffect(pair.proposal) {
        pair.proposal?.let { either ->
            either.fold(
                { onProposal(it) },
                { onAuthenticate(it) }
            )
        }
    }

    WcPairContent(
        modifier = modifier,
        pair = pair,
        textField = textField,
        onNavigateUp = onNavigateUp,
        onCancel = onCancel
    )
}

@Composable
fun WcPairContent(
    pair: PairState,
    textField: TextFieldState<PairingUri, WcFailure>,
    onNavigateUp: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    KoshScaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        title = { Text(stringResource(Res.string.wc_pair_title)) },
        onNavigateUp = { onNavigateUp() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
        ) {

            PairingUriTextField(
                modifier = Modifier.fillMaxWidth(),
                value = textField.value,
                error = textField.error?.firstOrNull(),
                readOnly = textField.readOnly,
                onChange = textField.onChange,
                label = { Text(stringResource(Res.string.wc_pair_uri_label)) },
                onDone = {
                    textField.validate().getOrNull()?.let {
                        pair.pair(it)
                    }
                },
            )

            PrimaryButtons(
                modifier = Modifier.padding(horizontal = 16.dp),
                cancel = {
                    TextButton(onClick = onCancel.single()) {
                        Text("Cancel")
                    }
                },
                confirm = {
                    LoadingButton(pair.loading, onClick = {
                        textField.validate().getOrNull()?.let {
                            pair.pair(it)
                        }
                    }) {
                        Text("Pair")
                    }
                }
            )
        }
    }
}

@Composable
fun PairingUriTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    error: WcFailure?,
    readOnly: Boolean = false,
    label: @Composable () -> Unit,
    onChange: (TextFieldValue) -> Unit,
    onDone: () -> Unit,
) {
    var focused by remember { mutableStateOf(false) }

    KoshTextField(
        modifier = modifier.onFocusChanged { focused = it.isFocused },
        value = value,
        onValueChange = { onChange(it) },
        error = error?.message,
        readOnly = readOnly,
        keyboardActions = KeyboardActions(onDone = {
            defaultKeyboardAction(ImeAction.Done)
            onDone()
        }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Uri,
        ),
        label = label,
        trailingIcon = nullUnless(focused) {
            PasteClearIcon(value) {
                onChange(it)
                if (it.text.isNotEmpty()) {
                    onDone()
                }
            }
        }
    )
}
