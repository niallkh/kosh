package kosh.eth.wallet

import okio.ByteString
import okio.ByteString.Companion.toByteString
import fr.acinq.secp256k1.Secp256k1 as LibSecp256k1

internal object Secp256k1 {
    fun sign(
        messageHash: ByteString,
        privateKey: ByteString,
    ): ByteString = LibSecp256k1.sign(
        message = messageHash.toByteArray(),
        privkey = privateKey.toByteArray()
    ).toByteString()

    fun recover(
        signature: ByteString,
        messageHash: ByteString,
        recId: Int,
    ): ByteString = LibSecp256k1.ecdsaRecover(
        sig = signature.toByteArray(),
        message = messageHash.toByteArray(),
        recid = recId
    ).toByteString()

    fun verify(
        signature: ByteString,
        messageHash: ByteString,
        publicKey: ByteString,
    ): Boolean = LibSecp256k1.verify(
        signature = signature.toByteArray(),
        message = messageHash.toByteArray(),
        pubkey = publicKey.toByteArray()
    )

    fun publicKey(
        privateKey: ByteString,
    ): ByteString = LibSecp256k1.pubkeyCreate(
        privkey = privateKey.toByteArray()
    ).toByteString()
}
