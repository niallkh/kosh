package kosh.eth.wallet

import kosh.eth.wallet.transaction.SignatureData
import okio.Buffer
import okio.ByteString

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
        pubkey = Secp256k1.publicKey(privkey.key)
    ) ?: error("Couldn't sign, rec id not found")

    return SignatureData(
        r = signature.substring(beginIndex = 0, endIndex = 32),
        s = signature.substring(beginIndex = 32, endIndex = 64),
        v = Buffer().apply { writeByte(recId + 27) }.readByteString()
    )
}

internal fun determineRecId(
    signature: ByteString,
    messageHash: ByteString,
    pubkey: ByteString,
): Int? {
    for (i in 0..3) {
        val recoveredPubkey = Secp256k1.recover(
            signature = signature,
            messageHash = messageHash,
            recId = i
        )

        if (pubkey == recoveredPubkey) {
            return i
        }
    }

    return null
}
