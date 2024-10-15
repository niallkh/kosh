package kosh.libs.ble

import kotlin.uuid.Uuid

data class BleConfig(
    val serviceUuids: List<Uuid>,
    val charNotifyUuids: List<Uuid>,
    val charWriteUuids: List<Uuid>,
    val maxWriteMtu: Int,
)
