package kosh.ui.trezor

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import kosh.domain.models.trezor.TrezorPasskey
import kosh.domain.models.trezor.TrezorPasskey.Index
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.single.single
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet

@Composable
fun TrezorPasskeysScreen(
    onUp: () -> Unit,
) {
//    val uiState by trezorPasskeysVM.state.collectState()
//
//    TrezorPasskeyContent(
//        uiState = { uiState },
//        onUp = onUp,
//        dispatch = { trezorPasskeysVM.dispatch(it) }
//    )
//
//    TrezorContent(
//        uiState = trezorPasskeysVM.trezorVM.provider(),
//        dispatcher = trezorPasskeysVM.trezorVM.dispatcher(),
//        onDismiss = onUp,
//    )
}

//@Composable
//fun TrezorPasskeyContent(
//    uiState: () -> UiState,
//    onUp: () -> Unit = {},
//    dispatch: (UiEvent) -> Unit = {},
//) {
//    KoshScaffold(
//        modifier = Modifier,
//        onUp = onUp,
//        title = {
//            if (!uiState().selectionMode) {
//                Text(text = stringResource(Res.string.trezor_passkeys_title))
//            } else {
//                Text(text = uiState().selectedPasskeys.size.toString())
//            }
//        },
//        actions = {
//            val selectionMode by derivedStateOf { uiState().selectionMode }
//
//            if (selectionMode) {
//                IconButton(onClick = { dispatch(UiEvent.Delete) }) {
//                    Icon(Icons.Default.Delete, contentDescription = "Delete")
//                }
//            } else {
//                IconButton(onClick = { dispatch(UiEvent.Export) }) {
//                    Icon(Icons.Default.Save, contentDescription = "Save")
//                }
//                IconButton(onClick = { dispatch(UiEvent.Import) }) {
//                    Icon(Icons.Default.FileOpen, contentDescription = "Open")
//                }
//            }
//        }
//    ) {
//
//        TrezorPasskeys(
//            modifier = Modifier,
//            passkeys = uiState().passkeys,
//            selectedPasskeys = uiState().selectedPasskeys,
//            loading = uiState().loading,
//            onSelect = { dispatch(UiEvent.Select(it)) },
//        )
//
//        AppFailureAlert(
//            failure = uiState().failures.firstOrNull(),
//            onDismiss = { dispatch(UiEvent.CancelFailure(it)) },
//        )
//    }
//
//    LaunchedEffect(Unit) {
//        snapshotFlow { uiState().finished }.first(::identity)
//        onUp()
//    }
//}

@Composable
fun TrezorPasskeys(
    modifier: Modifier = Modifier,
    passkeys: ImmutableList<TrezorPasskey>,
    selectedPasskeys: ImmutableSet<Index>,
    loading: Boolean,
    onSelect: (Index) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(passkeys) { passkey ->
            val selected by derivedStateOf { passkey.index in selectedPasskeys }

            TrezorPasskey(
                modifier = Modifier.animateItem(),
                passkey = passkey,
                selected = selected,
                onSelect = onSelect
            )
        }
    }

    LoadingIndicator(
        loading,
        Modifier,
    )
}

@Composable
fun TrezorPasskey(
    modifier: Modifier = Modifier,
    passkey: TrezorPasskey,
    selected: Boolean,
    onSelect: (Index) -> Unit,
) {
    val colors = if (selected.not()) ListItemDefaults.colors()
    else ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer)

    ListItem(
        modifier = modifier
            .clickable(onClick = { onSelect(passkey.index) }.single()),
        colors = colors,
        headlineContent = {
            Text(passkey.rpId ?: "")
        },
        supportingContent = {
            Text(passkey.userName ?: "")
        },
        leadingContent = {
            Image(
                modifier = Modifier.size(24.dp),
                painter = rememberImagePainter("https://${passkey.rpId}/favicon.ico"),
                contentDescription = "favicon",
            )
        }
    )
}
