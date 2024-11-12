package kosh.libs.keystone.coder

import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString
import kotlinx.io.write

object CRC32 {
    private val table by lazy {
        IntArray(256) { i ->
            (0 until 8).fold(i) { c, _ ->
                if (c and 1 != 0) (c ushr 1) xor 0xEDB88320.toInt()
                else c ushr 1
            }
        }
    }

    fun calculate(input: ByteString): Int {
        return sequence {
            repeat(input.size) {
                yield(input[it])
            }
        }
            .fold(0xFFFFFFFF.toInt()) { crc, byte ->
                (crc ushr 8) xor table[(crc xor byte.toInt()) and 0xFF]
            } xor 0xFFFFFFFF.toInt()
    }
}

internal fun ByteString.toInt(): Int {
    require(size == 4) { "ByteString must have exactly 4 bytes" }
    return Buffer().run {
        write(this@toInt)
        readInt()
    }
}

internal fun Int.toByteString(): ByteString {
    return Buffer().run {
        writeInt(this@toByteString)
        readByteString()
    }
}
