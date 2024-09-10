package kosh.libs.ledger

import arrow.fx.coroutines.Resource
import arrow.fx.coroutines.resource
import co.touchlab.kermit.Logger
import kosh.libs.usb.Device
import kosh.libs.usb.Usb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultLedgerManager(
    private val usb: Usb,
) : LedgerManager {

    private val logger = Logger.withTag("[K]LedgerManager")

    override val devices: Flow<List<LedgerDevice>>
        get() = usb.devices
            .map { it.mapNotNull(::mapLedgerDevice) }

    override suspend fun open(
        listener: LedgerManager.Listener,
        id: Long,
    ): Resource<LedgerManager.Connection> = resource {
        logger.i { "open(id = ${id})" }
        DefaultLedgerConnection(
            usbConnection = usb.open(id, ledgerUsbConfig).bind(),
            listener = listener,
        )
    }
}

internal fun mapLedgerDevice(device: Device): LedgerDevice? = if (
    device.vendorId in arrayOf(LEDGER_VENDOR_ID) &&
    (device.productId shr 8) in arrayOf(
        LEDGER_PRODUCT_ID_NANO_S,
        LEDGER_PRODUCT_ID_NANO_X,
        LEDGER_PRODUCT_ID_NANO_S_P,
        LEDGER_PRODUCT_ID_STAX,
    )
) {
    LedgerDevice(device.id, device.productName)
} else {
    null
}

