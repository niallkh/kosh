package kosh.libs.ledger

import arrow.fx.coroutines.Resource
import arrow.fx.coroutines.resource
import co.touchlab.kermit.Logger
import kosh.libs.ble.Ble
import kosh.libs.transport.Device
import kosh.libs.usb.Usb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class DefaultLedgerManager(
    private val usb: Usb,
    private val ble: Ble,
) : LedgerManager {

    private val logger = Logger.withTag("[K]LedgerManager")

    override val devices: Flow<List<LedgerDevice>>
        get() = combine(
            usb.devices(ledgerUsbConfig)
                .map { it.map { mapLedgerDevice(it, false) } },
            ble.devices(ledgerBleConfig)
                .map { it.map { mapLedgerDevice(it, true) } },
        ) { d1, d2 -> d1 + d2 }

    init {
        usb.register(ledgerUsbConfig)
        ble.register(ledgerBleConfig)
    }

    override suspend fun open(
        id: String,
        listener: LedgerManager.Listener,
    ): Resource<LedgerManager.Connection> = resource {
        logger.v { "open(id = ${id})" }

        val parsedId = LedgerDevice.parseId(id)

        if (LedgerDevice.isBle(id)) {
            DefaultLedgerConnection(
                connection = LedgerBleFormat(ble.open(parsedId, ledgerBleConfig).bind()),
                listener = listener,
            )
        } else {
            DefaultLedgerConnection(
                connection = LedgerUsbFormat(usb.open(parsedId, ledgerUsbConfig).bind()),
                listener = listener,
            )
        }
    }
}

internal fun mapLedgerDevice(
    device: Device,
    ble: Boolean,
): LedgerDevice = LedgerDevice(
    id = if (ble) LedgerDevice.bleId(device.id) else LedgerDevice.usbId(device.id),
    product = device.name,
    ble = ble
)

