package kosh.app.utils

import kosh.eth.abi.keccak256
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import platform.Foundation.NSData
import platform.Foundation.create
import platform.posix.memcpy

public fun keccak256(data: NSData): NSData {
    val hash = data.toByteString().keccak256()
    lateinit var result: NSData
    UnsafeByteStringOperations.withByteArrayUnsafe(hash) {
        result = it.toData()
    }
    return result
}

internal fun NSData.toByteArray(): ByteArray {
    val data = this
    val d = memScoped { data }
    return ByteArray(d.length.toInt()).apply {
        usePinned {
            memcpy(it.addressOf(0), d.bytes, d.length)
        }
    }
}

internal fun NSData.toByteString(): ByteString {
    return UnsafeByteStringOperations.wrapUnsafe(toByteArray())
}

internal fun ByteArray.toData(): NSData = memScoped {
    NSData.create(
        bytes = allocArrayOf(this@toData),
        length = this@toData.size.toULong()
    )
}

internal fun ByteString.toData(): NSData {
    lateinit var result: NSData
    UnsafeByteStringOperations.withByteArrayUnsafe(this) {
        result = it.toData()
    }
    return result
}
