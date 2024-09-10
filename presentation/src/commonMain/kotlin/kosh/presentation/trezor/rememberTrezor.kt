package kosh.presentation.trezor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kosh.domain.models.trezor.Trezor
import kosh.domain.usecases.trezor.TrezorService
import kosh.presentation.di.di

@Composable
fun rememberTrezor(
    trezorService: TrezorService = di { domain.trezorService },
): LedgerState {
    val trezor by trezorService.getCurrentDevice().collectAsState(null)

    return LedgerState(
        trezor = trezor
    )
}

@Immutable
data class LedgerState(
    val trezor: Trezor?,
)
