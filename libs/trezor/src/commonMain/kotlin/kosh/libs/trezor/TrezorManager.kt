package kosh.libs.trezor

import arrow.fx.coroutines.Resource
import com.satoshilabs.trezor.lib.protobuf.ButtonRequest
import com.satoshilabs.trezor.lib.protobuf.Failure
import com.satoshilabs.trezor.lib.protobuf.Features
import com.squareup.wire.Message
import kosh.libs.usb.UsbConfig
import kotlinx.coroutines.flow.Flow

private const val TREZOR_VENDOR_ID_ONE: Int = 0x534c
private const val TREZOR_PRODUCT_ID_ONE: Int = 0x0001
private const val TREZOR_VENDOR_ID: Int = 0x1209
private const val TREZOR_PRODUCT_ID: Int = 0x53c0
private const val TREZOR_PRODUCT_ID_2: Int = 0x53c1

internal val trezorUsbConfig = UsbConfig(
    usbInterfaceNumber = 0x00,
    packetSize = 64,
    vendorIds = listOf(TREZOR_VENDOR_ID, TREZOR_VENDOR_ID_ONE),
    productIds = listOf(TREZOR_PRODUCT_ID, TREZOR_PRODUCT_ID_2, TREZOR_PRODUCT_ID_ONE),
)

interface TrezorManager {

    val devices: Flow<List<TrezorDevice>>

    suspend fun open(
        id: Long,
        listener: Connection.Listener,
    ): Resource<Connection>

    interface Connection {

        suspend fun init(
            newSession: Boolean = false,
        ): Features

        suspend fun exchange(
            message: Message<*, *>,
        ): Message<*, *>

        interface Listener {

            suspend fun passphraseRequest(): String?

            suspend fun pinMatrixRequest(): String

            fun onButtonRequest(type: ButtonRequest.ButtonRequestType?)

            class TrezorFailureException(val type: Failure.FailureType?, message: String?) :
                RuntimeException(message)
        }
    }
}

data class TrezorDevice(
    val id: Long,
    val product: String?,
)
