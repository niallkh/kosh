package kosh.app.di.impl

import arrow.fx.coroutines.Resource
import kosh.app.di.TransportComponent
import kosh.domain.core.provider
import kosh.libs.ble.Ble
import kosh.libs.ble.IosBle
import kosh.libs.transport.Device
import kosh.libs.transport.Transport
import kosh.libs.usb.Usb
import kosh.libs.usb.UsbConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class IosTransportComponent : TransportComponent {

    override val ble: Ble by provider {
        IosBle()
    }

    override val usb: Usb by provider {
        object : Usb {
            override fun devices(config: UsbConfig): Flow<List<Device>> {
                return flowOf(listOf())
            }

            override suspend fun open(
                id: String,
                config: UsbConfig,
            ): Resource<Transport.Connection> {
                TODO("Not yet implemented")
            }
        }
    }
}
