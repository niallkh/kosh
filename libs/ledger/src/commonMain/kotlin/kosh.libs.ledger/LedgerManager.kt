package kosh.libs.ledger

import arrow.fx.coroutines.Resource
import kosh.libs.ble.BleConfig
import kosh.libs.usb.UsbConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.bytestring.ByteString
import kotlinx.io.write
import kotlin.uuid.Uuid

internal const val LEDGER_VENDOR_ID: Int = 0x2c97
internal const val LEDGER_PRODUCT_ID_NANO_S: Int = 0x10
internal const val LEDGER_PRODUCT_ID_NANO_X: Int = 0x40
internal const val LEDGER_PRODUCT_ID_NANO_S_P: Int = 0x50
internal const val LEDGER_PRODUCT_ID_STAX: Int = 0x60
internal const val LEDGER_PRODUCT_ID_FLEX: Int = 0x70

internal val ledgerUsbConfig = UsbConfig(
    usbInterfaceNumber = 0x00,
    packetSize = 64,
    vendorIds = listOf(LEDGER_VENDOR_ID),
    productIds = listOf(
        LEDGER_PRODUCT_ID_NANO_S,
        LEDGER_PRODUCT_ID_NANO_X,
        LEDGER_PRODUCT_ID_NANO_S_P,
        LEDGER_PRODUCT_ID_STAX,
    )
)

internal val ledgerBleConfig = BleConfig(
    serviceUuid = listOf(
        Uuid.parse("13d63400-2c97-0004-0000-4c6564676572"),
        Uuid.parse("13d63400-2c97-6004-0000-4c6564676572"),
        Uuid.parse("13d63400-2c97-3004-0000-4c6564676572"),
    ),
    charNotifyUuid = listOf(
        Uuid.parse("13d63400-2c97-0004-0001-4c6564676572"),
        Uuid.parse("13d63400-2c97-6004-0001-4c6564676572"),
        Uuid.parse("13d63400-2c97-3004-0001-4c6564676572"),
    ),
    charWriteUuid = listOf(
        Uuid.parse("13d63400-2c97-0004-0003-4c6564676572"),
        Uuid.parse("13d63400-2c97-6004-0003-4c6564676572"),
        Uuid.parse("13d63400-2c97-3004-0003-4c6564676572"),
//        Uuid.parse("13d63400-2c97-0004-0002-4c6564676572"),
//        Uuid.parse("13d63400-2c97-6004-0002-4c6564676572"),
//        Uuid.parse("13d63400-2c97-3004-0002-4c6564676572"),
    )
)

internal const val TAG = 0x05

interface LedgerManager {

    val devices: Flow<List<LedgerDevice>>

    suspend fun open(
        id: String,
        listener: Listener,
    ): Resource<Connection>

    interface Connection {

        suspend fun exchange(
            ledgerAPDU: LedgerAPDU,
        ): Pair<StatusWord, ByteString>
    }

    interface Listener {
        suspend fun buttonRequest(type: ButtonRequest)

        enum class ButtonRequest {
            UnlockDevice,
        }
    }
}

data class LedgerDevice(
    val id: String,
    val product: String?,
    val ble: Boolean,
) {
    companion object {
        fun usbId(id: String) = "usb:$id"

        fun bleId(id: String) = "ble:$id"

        fun isBle(id: String) = id.startsWith("ble:")

        fun parseId(id: String) = when {
            id.startsWith("usb:") -> id.removePrefix("usb:")
            id.startsWith("ble:") -> id.removePrefix("ble:")
            else -> id
        }
    }
}

suspend inline fun <T> LedgerManager.Connection.exchange(
    ledgerAPDU: LedgerAPDU,
    handleResponse: Source.(StatusWord) -> T,
): T {
    val (statusWord, response) = exchange(ledgerAPDU)
    val buffer = Buffer().apply { write(response) }
    return handleResponse(buffer, statusWord)
}
