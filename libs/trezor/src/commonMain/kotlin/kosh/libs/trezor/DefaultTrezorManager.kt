package kosh.libs.trezor

import arrow.fx.coroutines.Resource
import arrow.fx.coroutines.resource
import co.touchlab.kermit.Logger
import kosh.libs.transport.Device
import kosh.libs.usb.Usb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultTrezorManager(
    private val usb: Usb,
    private val sessionsCache: SessionsCache,
) : TrezorManager {

    private val logger = Logger.withTag("[K]TrezorManager")

    init {
        usb.register(trezorUsbConfig)
    }

    override val devices: Flow<List<TrezorDevice>>
        get() = usb.devices(trezorUsbConfig)
            .map { it.map(::mapTrezorDevice) }

    override suspend fun open(
        id: String,
        listener: TrezorManager.Connection.Listener,
    ): Resource<TrezorManager.Connection> = resource {
        logger.d { "open(id = ${id})" }
        DefaultTrezorConnection(
            connection = TrezorUsbFormat(usb.open(id, trezorUsbConfig).bind()),
            listener = listener,
            sessionCache = sessionsCache.create(id)
        )
    }
}

internal fun mapTrezorDevice(device: Device): TrezorDevice = TrezorDevice(
    id = device.id,
    product = device.name
)

