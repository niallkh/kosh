package kosh.libs.keystone

import arrow.fx.coroutines.Resource
import kosh.libs.usb.UsbConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.decodeToString
import kotlinx.io.bytestring.encodeToByteString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private const val KEYSTONE_VENDOR_ID: Int = 4617
private const val KEYSTONE_PRODUCT_ID: Int = 12289

internal val keystoneUsbConfig = UsbConfig(
    usbInterfaceNumber = 0x00,
    packetSize = 64,
    vendorIds = listOf(KEYSTONE_VENDOR_ID),
    productIds = listOf(KEYSTONE_PRODUCT_ID),
)

interface KeystoneManager {

    val devices: Flow<List<KeystoneDevice>>

    suspend fun open(
        id: String,
        listener: Connection.Listener,
    ): Resource<Connection>

    interface Connection {
        suspend fun exchange(
            command: Actions,
            data: ByteString,
        ): Pair<StatusCode, ByteString>

        interface Listener
    }
}

suspend inline fun <T> KeystoneManager.Connection.resolveUr(
    data: String,
    handleResponse: (StatusCode, String) -> T,
): T {
    val (statusCode, response) = exchange(
        command = Actions.CMD_RESOLVE_UR,
        data = data.encodeToByteString(),
    )

    val payload = Json.decodeFromString<JsonElement>(
        string = response.decodeToString()
    ).jsonObject["payload"]?.jsonPrimitive?.content.orEmpty()

    return handleResponse(statusCode, payload)
}


