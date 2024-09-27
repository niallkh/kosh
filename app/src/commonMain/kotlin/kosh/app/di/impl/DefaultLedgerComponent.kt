package kosh.app.di.impl

import kosh.app.di.TransportComponent
import kosh.data.trezor.LedgerComponent
import kosh.domain.core.provider
import kosh.libs.ledger.DefaultLedgerManager
import kosh.libs.ledger.LedgerManager

class DefaultLedgerComponent(
    transportComponent: TransportComponent,
) : LedgerComponent, TransportComponent by transportComponent {

    override val ledgerManager: LedgerManager by provider {
        DefaultLedgerManager(
            usb = usb,
            ble = ble,
        )
    }
}
