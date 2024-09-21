package kosh.libs.usb

import arrow.fx.coroutines.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.io.Sink
import kotlinx.io.Source

interface Usb {

    val devices: Flow<List<Device>>

    /** @throws PermissionNotGrantedException */
    suspend fun open(
        id: Long,
        config: DeviceConfig,
    ): Resource<Connection>

    interface Connection : AutoCloseable {

        suspend fun write(
            source: Source,
        )

        suspend fun read(
            sink: Sink,
            length: Int,
        )
    }
}

class PermissionNotGrantedException : RuntimeException("Usb permission not granted for device")
class UsbInterfaceNotClaimedException : RuntimeException("Usb interface not claimed")
