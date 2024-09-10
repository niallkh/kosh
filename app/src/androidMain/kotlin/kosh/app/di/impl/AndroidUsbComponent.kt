package kosh.app.kosh.app.di.impl

import kosh.app.di.AndroidComponent
import kosh.app.di.UsbComponent
import kosh.domain.core.provider
import kosh.libs.usb.AndroidUsb
import kosh.libs.usb.Usb

class AndroidUsbComponent(
    androidComponent: AndroidComponent,
) : UsbComponent, AndroidComponent by androidComponent {

    override val usb: Usb by provider {
        AndroidUsb(context = context)
    }
}
