package kosh.app.di

import kosh.domain.core.provider
import kosh.libs.ble.AndroidBle
import kosh.libs.ble.Ble
import kosh.libs.usb.AndroidUsb
import kosh.libs.usb.Usb

internal class AndroidTransportComponent(
    androidComponent: AndroidComponent,
) : TransportComponent, AndroidComponent by androidComponent {

    override val usb: Usb by provider {
        AndroidUsb(context = context)
    }

    override val ble: Ble by provider {
        AndroidBle(context = context)
    }
}
