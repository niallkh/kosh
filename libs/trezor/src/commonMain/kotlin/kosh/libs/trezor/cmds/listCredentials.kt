package kosh.libs.trezor.cmds

import com.satoshilabs.trezor.lib.protobuf.Success
import com.satoshilabs.trezor.lib.protobuf.WebAuthnAddResidentCredential
import com.satoshilabs.trezor.lib.protobuf.WebAuthnCredentials
import com.satoshilabs.trezor.lib.protobuf.WebAuthnListResidentCredentials
import com.satoshilabs.trezor.lib.protobuf.WebAuthnRemoveResidentCredential
import kosh.libs.trezor.TrezorManager
import kotlinx.io.bytestring.ByteString

suspend fun TrezorManager.Connection.listCredentials(): List<WebAuthnCredentials.WebAuthnCredential> {
    val response = exchange(WebAuthnListResidentCredentials())
    return response.expect<WebAuthnCredentials>().credentials
}

suspend fun TrezorManager.Connection.deleteCredential(index: Int) {
    exchange(WebAuthnRemoveResidentCredential(index = index))
        .expect<Success>()
}

suspend fun TrezorManager.Connection.addCredential(id: ByteString) {
    exchange(WebAuthnAddResidentCredential(credential_id = id.toOkio()))
        .expect<Success>()
}
