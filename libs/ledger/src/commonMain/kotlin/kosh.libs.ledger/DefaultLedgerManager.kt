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
            usb.devices(ledgerUsbConfig),
            ble.devices(ledgerBleConfig),
        ) { d1, d2 -> d1 + d2 }
            .map { it.map(::mapLedgerDevice) }

    override suspend fun open(
        listener: LedgerManager.Listener,
        id: String,
    ): Resource<LedgerManager.Connection> = resource {
        logger.i { "open(id = ${id})" }

        id.toLongOrNull()?.let {
            DefaultLedgerConnection(
                connection = LedgerUsbFormat(usb.open(id, ledgerUsbConfig).bind()),
                listener = listener,
            )
        } ?: DefaultLedgerConnection(
            connection = LedgerBleFormat(ble.open(id, ledgerBleConfig).bind()),
            listener = listener,
        )
    }
}

internal fun mapLedgerDevice(device: Device): LedgerDevice =
    LedgerDevice(device.id, device.name)

