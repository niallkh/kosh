package kosh.libs.keystone

import arrow.fx.coroutines.Resource
import arrow.fx.coroutines.resource
import co.touchlab.kermit.Logger
import kosh.libs.transport.Device
import kosh.libs.usb.Usb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultKeystoneManager(
    private val usb: Usb,
) : KeystoneManager {

    private val logger = Logger.withTag("[K]KeystoneManager")

    init {
        usb.register(keystoneUsbConfig)
    }

    override val devices: Flow<List<KeystoneDevice>>
        get() = usb.devices(keystoneUsbConfig)
            .map { it.map(::mapTrezorDevice) }

    override suspend fun open(
        id: String,
        listener: KeystoneManager.Connection.Listener,
    ): Resource<KeystoneManager.Connection> = resource {
        logger.v { "open(id = ${id})" }
        DefaultKeystoneConnection(
            connection = KeystoneUsbFormat(usb.open(id, keystoneUsbConfig).bind()),
            listener = listener,
        )
    }
}

internal fun mapTrezorDevice(device: Device): KeystoneDevice = KeystoneDevice(
    id = device.id,
    product = device.name
)

