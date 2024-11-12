package kosh.libs.keystone.cmds

import kosh.libs.keystone.KeystoneManager
import kosh.libs.keystone.bc.CryptoKeypath
import kosh.libs.keystone.bc.DataType
import kosh.libs.keystone.bc.EthSignRequest
import kosh.libs.keystone.bc.EthSignature
import kosh.libs.keystone.expectSuccess
import kosh.libs.keystone.resolveUr
import kotlinx.io.bytestring.ByteString

suspend fun KeystoneManager.Connection.signTypedMessage(
    derivationPath: List<UInt>,
    masterFingerprint: ULong,
    eip712: ByteString,
) = resolveUr(
    EthSignRequest(
        signData = eip712,
        derivationPath = CryptoKeypath(derivationPath, masterFingerprint),
        dataType = DataType.TypedData,
    ).toUr(),
) { sc, ur ->
    sc.expectSuccess(ur)
    EthSignature(ur).signature
}
