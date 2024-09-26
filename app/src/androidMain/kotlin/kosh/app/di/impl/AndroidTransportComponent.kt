package kosh.app.di.impl

import kosh.app.di.AndroidComponent
import kosh.app.di.TransportComponent
import kosh.domain.core.provider
import kosh.libs.ble.AndroidBle
import kosh.libs.ble.Ble
import kosh.libs.usb.AndroidUsb
import kosh.libs.usb.Usb

class AndroidTransportComponent(
    androidComponent: AndroidComponent,
) : TransportComponent, AndroidComponent by androidComponent {

    override val usb: Usb by provider {
        AndroidUsb(context = context)
    }

    override val ble: Ble by provider {
        AndroidBle(context = context)
    }
}
