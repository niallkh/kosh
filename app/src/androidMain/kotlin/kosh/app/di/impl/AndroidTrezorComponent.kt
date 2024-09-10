package kosh.app.di.impl

import kosh.app.di.UsbComponent
import kosh.data.trezor.TrezorComponent
import kosh.domain.core.provider
import kosh.libs.trezor.DefaultTrezorManager
import kosh.libs.trezor.SessionsCache
import kosh.libs.trezor.TrezorManager

class AndroidTrezorComponent(
    usbComponent: UsbComponent,
) : TrezorComponent, UsbComponent by usbComponent {
    override val trezorManager: TrezorManager by provider {
        DefaultTrezorManager(
            usb = usb,
            sessionsCache = SessionsCache()
        )
    }
}
