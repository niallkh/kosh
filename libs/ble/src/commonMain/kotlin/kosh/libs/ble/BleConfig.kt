package kosh.libs.ble

import kotlin.uuid.Uuid

data class BleConfig(
    val serviceUuid: List<Uuid>,
    val charNotifyUuid: List<Uuid>,
    val charWriteUuid: List<Uuid>,
)
