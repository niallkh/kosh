package kosh.domain.utils

import kotlinx.io.Source
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import kotlinx.io.readByteArray
import org.kotlincrypto.hash.md.MD5

fun ByteString.md5(): ByteString {
    lateinit var result: ByteString
    UnsafeByteStringOperations.withByteArrayUnsafe(this) {
        result = UnsafeByteStringOperations.wrapUnsafe(MD5().digest(it))
    }
    return result
}

fun Source.md5(): ByteString {
    return if (request(4096)) {
        val buffer = ByteArray(4096)
        val md5 = MD5()
        while (!exhausted()) {
            val size = readAtMostTo(buffer)
            md5.update(buffer, 0, size)
        }
        UnsafeByteStringOperations.wrapUnsafe(md5.digest())
    } else {
        UnsafeByteStringOperations.wrapUnsafe(MD5().digest(readByteArray()))
    }
}
