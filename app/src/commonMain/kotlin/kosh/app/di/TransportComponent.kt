package kosh.app.di

import kosh.libs.ble.Ble
import kosh.libs.usb.Usb

public interface TransportComponent {

    public val usb: Usb

    public val ble: Ble
}
