package kosh.libs.ble

import kosh.libs.transport.Transport
import kotlin.uuid.Uuid

internal val CCC_UUID = Uuid.parse("00002902-0000-1000-8000-00805f9b34fb")

interface Ble : Transport<BleConfig>
