package kosh.presentation.keystone

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import kosh.domain.repositories.KeystoneListener

@Composable
fun rememberKeystoneListener(): KeystoneListenerState {
    val listener = remember {
        object : KeystoneListener {
        }
    }

    return KeystoneListenerState(
        listener = listener,
    )
}


@Immutable
data class KeystoneListenerState(
    val listener: KeystoneListener,
)
