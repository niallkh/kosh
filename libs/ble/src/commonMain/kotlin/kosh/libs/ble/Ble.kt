package kosh.libs.ble

import kosh.libs.transport.Transport
import kotlin.uuid.Uuid

internal val CCC_UUID = Uuid.parse("00002902-0000-1000-8000-00805f9b34fb")
internal const val MTU_HEADER = 5
internal const val MTU_MIN = 23 - MTU_HEADER
internal const val MTU_MAX = 517 - MTU_HEADER

interface Ble : Transport<BleConfig>
