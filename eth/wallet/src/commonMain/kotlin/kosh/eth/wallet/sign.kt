package kosh.eth.wallet

import kosh.eth.wallet.transaction.SignatureData
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString

internal fun sign(
    privkey: PrivateKey,
    messageHash: ByteString,
): SignatureData {
    val signature = Secp256k1.sign(
        messageHash = messageHash,
        privateKey = privkey.key
    )

    val recId = determineRecId(
        signature = signature,
        messageHash = messageHash,
        publicKey = Secp256k1.publicKey(privkey.key)
    ) ?: error("Couldn't sign, rec id not found")

    return SignatureData(
        r = signature.substring(0, 32),
        s = signature.substring(32, 64),
        v = Buffer().apply { writeByte((recId + 27u).toByte()) }.readByteString()
    )
}

internal fun determineRecId(
    signature: ByteString,
    messageHash: ByteString,
    publicKey: ByteString,
): UInt? {
    for (i in 0u..3u) {
        val recoveredPublicKey = Secp256k1.recover(
            signature = signature,
            messageHash = messageHash,
            recId = i.toInt()
        )

        if (publicKey == recoveredPublicKey) {
            return i
        }
    }

    return null
}
