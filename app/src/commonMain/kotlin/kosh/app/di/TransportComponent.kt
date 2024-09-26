package kosh.app.di

import kosh.libs.ble.Ble
import kosh.libs.usb.Usb

interface TransportComponent {

    val usb: Usb

    val ble: Ble
}
