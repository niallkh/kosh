package kosh.presentation.trezor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import arrow.core.toOption
import kosh.domain.failure.AppFailure
import kosh.domain.models.trezor.Trezor
import kosh.domain.usecases.trezor.TrezorService
import kosh.presentation.Collect
import kosh.presentation.di.di
import kotlinx.coroutines.flow.map

@Composable
fun rememberTrezor(
    trezorService: TrezorService = di { domain.trezorService },
): TrezorState {
    val trezor = Collect<AppFailure, _> {
        trezorService.getCurrentDevice()
            .map { it.toOption() }
    }

    return TrezorState(
        trezor = trezor.content?.getOrNull()
    )
}

@Immutable
data class TrezorState(
    val trezor: Trezor?,
)
