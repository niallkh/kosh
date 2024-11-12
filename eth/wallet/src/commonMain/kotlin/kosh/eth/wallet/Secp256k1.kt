package kosh.eth.wallet

import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import fr.acinq.secp256k1.Secp256k1 as LibSecp256k1

internal object Secp256k1 {
    fun sign(
        messageHash: ByteString,
        privateKey: ByteString,
    ): ByteString {
        lateinit var signature: ByteString
        UnsafeByteStringOperations.withByteArrayUnsafe(messageHash) { hash ->
            UnsafeByteStringOperations.withByteArrayUnsafe(privateKey) { pk ->
                signature = UnsafeByteStringOperations.wrapUnsafe(
                    LibSecp256k1.sign(
                        message = hash,
                        privkey = pk
                    )
                )
            }
        }
        return signature
    }

    fun recover(
        signature: ByteString,
        messageHash: ByteString,
        recId: Int,
    ): ByteString {
        lateinit var recovered: ByteString
        UnsafeByteStringOperations.withByteArrayUnsafe(signature) { sig ->
            UnsafeByteStringOperations.withByteArrayUnsafe(messageHash) { hash ->
                recovered = UnsafeByteStringOperations.wrapUnsafe(
                    LibSecp256k1.ecdsaRecover(
                        sig = sig,
                        message = hash,
                        recid = recId
                    )
                )
            }
        }
        return recovered
    }

    fun verify(
        signature: ByteString,
        messageHash: ByteString,
        publicKey: ByteString,
    ): Boolean {
        var verified: Boolean
        UnsafeByteStringOperations.withByteArrayUnsafe(signature) { sig ->
            UnsafeByteStringOperations.withByteArrayUnsafe(messageHash) { hash ->
                UnsafeByteStringOperations.withByteArrayUnsafe(publicKey) { pk ->
                    verified = LibSecp256k1.verify(
                        signature = sig,
                        message = hash,
                        pubkey = pk
                    )

                }
            }
        }
        return verified
    }

    fun publicKey(
        privateKey: ByteString,
    ): ByteString {
        lateinit var publicKey: ByteString
        UnsafeByteStringOperations.withByteArrayUnsafe(privateKey) { pk ->
            publicKey = UnsafeByteStringOperations.wrapUnsafe(
                LibSecp256k1.pubkeyCreate(pk)
            )
        }
        return publicKey
    }

    fun publicKeyParse(
        compressedPublicKey: ByteString,
    ): ByteString {
        lateinit var publicKey: ByteString
        UnsafeByteStringOperations.withByteArrayUnsafe(compressedPublicKey) { pk ->
            publicKey = UnsafeByteStringOperations.wrapUnsafe(
                LibSecp256k1.pubkeyParse(pk)
            )
        }
        return publicKey
    }
}
