package kosh.eth.abi

import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import org.kotlincrypto.hash.sha3.Keccak256

public fun ByteString.keccak256(): ByteString {
    lateinit var result: ByteString
    UnsafeByteStringOperations.withByteArrayUnsafe(this) {
        result = UnsafeByteStringOperations.wrapUnsafe(Keccak256().digest(it))
    }
    return result
}
