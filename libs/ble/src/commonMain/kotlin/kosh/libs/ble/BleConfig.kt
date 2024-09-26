package kosh.libs.ble

import kotlin.uuid.Uuid

data class BleConfig(
    val serviceUuid: List<Uuid>,
    val notifyUuid: List<Uuid>,
    val writeUuid: List<Uuid>,
)
