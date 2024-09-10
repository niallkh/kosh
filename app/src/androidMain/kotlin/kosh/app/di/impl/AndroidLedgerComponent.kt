package kosh.app.kosh.app.di.impl

import kosh.app.di.UsbComponent
import kosh.data.trezor.LedgerComponent
import kosh.domain.core.provider
import kosh.libs.ledger.DefaultLedgerManager
import kosh.libs.ledger.LedgerManager

class AndroidLedgerComponent(
    usbComponent: UsbComponent,
) : LedgerComponent, UsbComponent by usbComponent {
    override val ledgerManager: LedgerManager by provider {
        DefaultLedgerManager(
            usb = usb,
        )
    }
}
