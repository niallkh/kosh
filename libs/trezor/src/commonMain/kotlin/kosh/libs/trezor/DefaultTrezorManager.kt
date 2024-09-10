package kosh.libs.trezor

import arrow.fx.coroutines.Resource
import arrow.fx.coroutines.resource
import co.touchlab.kermit.Logger
import kosh.libs.usb.Device
import kosh.libs.usb.Usb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultTrezorManager(
    private val usb: Usb,
    private val sessionsCache: SessionsCache,
) : TrezorManager {

    private val logger = Logger.withTag("[K]TrezorManager")

    override val devices: Flow<List<TrezorDevice>>
        get() = usb.devices
            .map { it.mapNotNull(::mapTrezorDevice) }

    override suspend fun open(
        id: Long,
        listener: TrezorManager.Connection.Listener,
    ): Resource<TrezorManager.Connection> = resource {
        logger.i { "open(id = ${id})" }
        DefaultTrezorConnection(
            usbConnection = usb.open(id, trezorUsbConfig).bind(),
            listener = listener,
            sessionCache = sessionsCache.create(id)
        )
    }
}

internal fun mapTrezorDevice(device: Device): TrezorDevice? = if (
    device.vendorId in arrayOf(TREZOR_VENDOR_ID, TREZOR_VENDOR_ID_ONE) &&
    device.productId in arrayOf(TREZOR_PRODUCT_ID, TREZOR_PRODUCT_ID_2, TREZOR_PRODUCT_ID_ONE)
) {
    TrezorDevice(device.id, device.productName)
} else {
    null
}

