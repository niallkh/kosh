package kosh.libs.usb

data class UsbConfig(
    val vendorIds: List<Int>,
    val productIds: List<Int>,
    val usbInterfaceNumber: Int,
    val packetSize: Int,
)
