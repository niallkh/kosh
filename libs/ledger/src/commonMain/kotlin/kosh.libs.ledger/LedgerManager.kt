package kosh.libs.ledger

import arrow.fx.coroutines.Resource
import kosh.libs.usb.DeviceConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.bytestring.ByteString
import kotlinx.io.write

internal const val LEDGER_VENDOR_ID: Int = 0x2c97
internal const val LEDGER_PRODUCT_ID_NANO_S: Int = 0x10
internal const val LEDGER_PRODUCT_ID_NANO_X: Int = 0x40
internal const val LEDGER_PRODUCT_ID_NANO_S_P: Int = 0x50
internal const val LEDGER_PRODUCT_ID_STAX: Int = 0x60
internal const val LEDGER_PRODUCT_ID_FLEX: Int = 0x70

internal val ledgerUsbConfig = DeviceConfig(
    usbInterfaceNumber = 0x00,
    packetSize = PACKET_SIZE
)

interface LedgerManager {

    val devices: Flow<List<LedgerDevice>>

    suspend fun open(
        listener: Listener,
        id: Long,
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
    val id: Long,
    val product: String?,
)

suspend inline fun <T> LedgerManager.Connection.exchange(
    ledgerAPDU: LedgerAPDU,
    handleResponse: Source.(StatusWord) -> T,
): T {
    val (statusWord, response) = exchange(ledgerAPDU)
    val buffer = Buffer().apply { write(response) }
    return handleResponse(buffer, statusWord)
}
