package kosh.libs.keystone.cmds

import kosh.libs.keystone.KeystoneManager
import kosh.libs.keystone.bc.CryptoKeypath
import kosh.libs.keystone.bc.DataType
import kosh.libs.keystone.bc.EthSignRequest
import kosh.libs.keystone.bc.EthSignature
import kosh.libs.keystone.expectSuccess
import kosh.libs.keystone.resolveUr
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString
import kotlinx.io.write

suspend fun KeystoneManager.Connection.signTransaction(
    derivationPath: List<UInt>,
    masterFingerprint: ULong,
    rawTransaction: ByteString,
) = resolveUr(
    EthSignRequest(
        signData = rawTransaction,
        derivationPath = CryptoKeypath(derivationPath, masterFingerprint),
        dataType = DataType.TypedTransaction,
    ).toUr(),
) { sc, ur ->
    sc.expectSuccess(ur)
    val signature = EthSignature(ur).signature
    Buffer().run {
        write(signature, endIndex = 64)
        writeByte((signature[64] + 27).toByte())
        readByteString()
    }
}
