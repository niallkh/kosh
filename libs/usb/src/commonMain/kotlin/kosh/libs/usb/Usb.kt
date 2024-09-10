package kosh.libs.usb

import arrow.fx.coroutines.Resource
import kotlinx.coroutines.flow.Flow
import okio.BufferedSink
import okio.BufferedSource

interface Usb {

    val devices: Flow<List<Device>>

    /** @throws PermissionNotGrantedException */
    suspend fun open(
        id: Long,
        config: DeviceConfig,
    ): Resource<Connection>

    interface Connection : AutoCloseable {

        suspend fun write(
            source: BufferedSource,
        )

        suspend fun read(
            sink: BufferedSink,
            length: Int,
        )
    }
}

class PermissionNotGrantedException : RuntimeException("Usb permission not granted for device")
class UsbInterfaceNotClaimedException : RuntimeException("Usb interface not claimed")
