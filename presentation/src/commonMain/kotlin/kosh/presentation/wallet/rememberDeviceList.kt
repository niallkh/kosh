package kosh.presentation.wallet

import androidx.compose.runtime.Composable
import kosh.domain.failure.AppFailure
import kosh.domain.models.hw.HardwareWallet
import kosh.domain.usecases.ledger.LedgerService
import kosh.domain.usecases.trezor.TrezorService
import kosh.presentation.Collect
import kosh.presentation.di.di

@Composable
fun rememberHardwareWallets(
    trezorService: TrezorService = di { domain.trezorService },
    ledgerService: LedgerService = di { domain.ledgerService },
): DeviceListState {
    val trezors = Collect<AppFailure, _> {
        trezorService.list()
    }

    val ledgers = Collect<AppFailure, _> {
        ledgerService.list()
    }

    return DeviceListState(
        wallets = (trezors.content ?: listOf()) + (ledgers.content ?: listOf()),
    )
}

data class DeviceListState(
    val wallets: List<HardwareWallet>,
)
