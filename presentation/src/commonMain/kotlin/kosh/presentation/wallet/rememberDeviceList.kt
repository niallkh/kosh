package kosh.presentation.wallet

import androidx.compose.runtime.Composable
import kosh.domain.failure.AppFailure
import kosh.domain.models.hw.HardwareWallet
import kosh.domain.serializers.ImmutableList
import kosh.domain.usecases.ledger.LedgerService
import kosh.domain.usecases.trezor.TrezorService
import kosh.presentation.Collect
import kosh.presentation.di.di
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.plus
import kotlinx.collections.immutable.toPersistentList

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

    val trezors1 = trezors.content?.toPersistentList() ?: persistentListOf()
    val ledgers1 = ledgers.content?.toPersistentList() ?: persistentListOf()
    return DeviceListState(
        wallets = trezors1 + ledgers1,
    )
}

data class DeviceListState(
    val wallets: ImmutableList<HardwareWallet>,
)
