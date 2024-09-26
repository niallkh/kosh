package kosh.libs.usb

import arrow.fx.coroutines.Resource
import arrow.fx.coroutines.resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import javax.usb.UsbConst
import javax.usb.UsbDevice
import javax.usb.UsbEndpoint
import javax.usb.UsbHostManager
import javax.usb.UsbHub
import javax.usb.UsbInterface
import kotlin.math.abs
import kotlin.time.Duration.Companion.seconds

class JvmUsb : Usb {

    override val devices: Flow<List<Device>>
        get() = listDevices()

    override suspend fun open(
        id: Long,
        config: UsbConfig,
    ): Resource<Usb.Connection> = withContext(Dispatchers.IO) {
        val usbServices = UsbHostManager.getUsbServices()
        val device = requireNotNull(
            usbServices.rootUsbHub.listDevices()
                .find { abs(it.hashCode()).toLong() == id }) {
            "Device not found"
        }

        resource {
            val usbInterface = usbInterface(device, config)
            usbInterface.claim { false }
            val writePipe = usbEndpoint(config, usbInterface, true).usbPipe
            val readPipe = usbEndpoint(config, usbInterface, false).usbPipe

            install(
                acquire = {
                    JvmUsbConnection(
                        usbInterface = usbInterface,
                        writePipe = writePipe,
                        readPipe = readPipe,
                    )
                },
                release = { it, _ -> it.close() }
            )
        }
    }
}

private fun listDevices(): Flow<List<Device>> = flow {
    val usbServices = UsbHostManager.getUsbServices()
    while (currentCoroutineContext().isActive) {
        val devices = usbServices.rootUsbHub.listDevices()
            .mapNotNull { it.usbDeviceData() }
        emit(devices)
        delay(1.seconds)
    }
}

private fun UsbDevice.usbDeviceData(): Device? = if (!isUsbHub)
    Device(
        id = abs(hashCode().toLong()),
        vendorId = usbDeviceDescriptor.idVendor().toInt(),
        productId = usbDeviceDescriptor.idProduct().toInt(),
        productName = productString,
        manufacturerName = manufacturerString,
    )
else null

private fun UsbHub.listDevices(): Set<UsbDevice> {
    val hubQueue = mutableListOf(this)
    val devices = mutableSetOf<UsbDevice>()

    while (hubQueue.isNotEmpty()) {
        hubQueue.removeFirst().attachedUsbDevices.forEach { device ->
            when (device) {
                is UsbHub -> hubQueue.add(device)
                is UsbDevice -> devices.add(device)
                else -> error("Invalid usb device")
            }
        }
    }

    return devices
}

private fun usbInterface(
    usbDevice: UsbDevice,
    config: UsbConfig,
): UsbInterface {
    return usbDevice.activeUsbConfiguration
        .getUsbInterface(config.usbInterfaceNumber.toByte())
        ?: error("Interface not found")
}

private fun usbEndpoint(
    config: UsbConfig,
    usbInterface: UsbInterface,
    write: Boolean,
): UsbEndpoint {
    return usbInterface.usbEndpoints.asSequence()
        .map { it as UsbEndpoint }
        .filter { it.type == UsbConst.ENDPOINT_TYPE_INTERRUPT }
        .filter { it.usbEndpointDescriptor.wMaxPacketSize().toInt() == config.packetSize }
        .filter {
            if (write) it.direction == UsbConst.ENDPOINT_DIRECTION_OUT
            else it.direction == UsbConst.ENDPOINT_DIRECTION_IN
        }
        .firstOrNull() ?: error("Usb endpoint not found")
}
