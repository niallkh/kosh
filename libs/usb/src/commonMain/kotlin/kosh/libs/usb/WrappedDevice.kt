package kosh.libs.usb

import kosh.libs.transport.Device

internal data class WrappedDevice(
    val device: Device,
    val vendorId: Int,
    val productId: Int,
)
