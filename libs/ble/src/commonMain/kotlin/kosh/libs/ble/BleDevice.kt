package kosh.libs.ble

import kosh.libs.transport.Device
import kotlin.uuid.Uuid

data class BleDevice(
    val device: Device,
    val services: List<Uuid>,
)
