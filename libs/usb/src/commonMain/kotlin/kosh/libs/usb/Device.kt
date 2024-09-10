package kosh.libs.usb

data class Device(
    val id: Long,
    val vendorId: Int,
    val productId: Int,
    val productName: String?,
    val manufacturerName: String?,
)

data class DeviceConfig(
    val usbInterfaceNumber: Int,
//    val writeEndpoints: List<Int>,
//    val readEndpoint: Int,
    val packetSize: Int,
)
