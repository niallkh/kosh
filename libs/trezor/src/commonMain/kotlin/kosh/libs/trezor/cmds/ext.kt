package kosh.libs.trezor.cmds

import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import okio.ByteString.Companion.toByteString


fun ByteString.toOkio(): okio.ByteString {
    lateinit var result: okio.ByteString
    UnsafeByteStringOperations.withByteArrayUnsafe(this) {
        result = it.toByteString()
    }
    return result
}

fun okio.ByteString.toIo(): ByteString {
    return UnsafeByteStringOperations.wrapUnsafe(toByteArray())
}
