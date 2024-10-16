package kosh.presentation.wallet

import androidx.compose.runtime.Composable
import kosh.domain.models.hw.HardwareWallet
import kosh.domain.serializers.ImmutableList
import kosh.domain.usecases.ledger.LedgerService
import kosh.domain.usecases.trezor.TrezorService
import kosh.presentation.core.di
import kosh.presentation.rememberCollect
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.plus
import kotlinx.collections.immutable.toPersistentList

@Composable
fun rememberHardwareWallets(
    trezorService: TrezorService = di { domain.trezorService },
    ledgerService: LedgerService = di { domain.ledgerService },
): DeviceListState {
    val trezors = rememberCollect(persistentListOf()) {
        trezorService.list()
    }

    val ledgers = rememberCollect(persistentListOf()) {
        ledgerService.list()
    }

    return DeviceListState(
        wallets = trezors.result.toPersistentList() + ledgers.result.toPersistentList(),
    )
}

data class DeviceListState(
    val wallets: ImmutableList<HardwareWallet>,
)
