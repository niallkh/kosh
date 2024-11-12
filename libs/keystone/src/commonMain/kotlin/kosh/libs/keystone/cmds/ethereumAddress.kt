package kosh.libs.keystone.cmds

import kosh.eth.proposals.eip55.eip55
import kosh.eth.wallet.Wallet
import kosh.libs.keystone.KeystoneManager
import kosh.libs.keystone.bc.CryptoKeypath
import kosh.libs.keystone.bc.CryptoMultiAccounts
import kosh.libs.keystone.bc.KeyDerivation
import kosh.libs.keystone.bc.KeyDerivationSchema
import kosh.libs.keystone.bc.QRHardwareCall
import kosh.libs.keystone.bc.QRHardwareCallType
import kosh.libs.keystone.bc.QRHardwareCallVersion
import kosh.libs.keystone.expectSuccess
import kosh.libs.keystone.resolveUr


suspend fun KeystoneManager.Connection.ethereumAddress(
    derivationPath: List<UInt>,
): String = resolveUr(
    QRHardwareCall(
        type = QRHardwareCallType.KeyDerivation,
        params = KeyDerivation(listOf(KeyDerivationSchema(CryptoKeypath(derivationPath)))),
        version = QRHardwareCallVersion.V1,
    ).toUr(),
) { sc, ur ->
    sc.expectSuccess(ur)
    val cryptoMultiAccounts = CryptoMultiAccounts(ur)
    val compressedPublicKey = cryptoMultiAccounts.keys[0].key ?: error("missing key")
    Wallet.address(compressedPublicKey).eip55()
}
