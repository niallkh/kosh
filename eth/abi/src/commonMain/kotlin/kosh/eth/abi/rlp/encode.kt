package kosh.eth.abi.rlp

import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString
import kotlinx.io.write

internal const val OFFSET_SHORT_STRING = 0x80u
internal const val OFFSET_SHORT_LIST = 0xc0u

internal fun Sink.encode(type: Rlp): Unit = when (type) {
    is Rlp.RlpList -> list(type)
    is Rlp.RlpString -> string(type)
}

private fun Sink.string(string: Rlp.RlpString) {
    encodeString(string.bytes)
}

private fun Sink.list(list: Rlp.RlpList) {
    if (list.isEmpty()) {
        encodeListSize(0u)
    } else {
        val size = list.fold(0u) { acc, type ->
            acc + estimate(type)
        }
        encodeListSize(size)

        for (entry in list) {
            encode(entry)
        }
    }
}

/**
 * Expected :c8c0c2c0c4c0c2c0
 * Actual   :c7c0c1c0c3c0c1c0
 */
private fun Sink.encodeString(bytes: ByteString) {
    when {
        bytes.size == 1
                && bytes[0].toUByte() in 0x00u until OFFSET_SHORT_STRING -> write(bytes)

        bytes.size <= 55 -> {
            writeByte((OFFSET_SHORT_STRING + bytes.size.toUInt()).toByte())
            write(bytes)
        }

        else -> {
            val encodedStringLength = toByteArray(bytes.size)
            writeByte(((OFFSET_SHORT_STRING + 55u + encodedStringLength.size.toUInt()).toByte()))
            write(encodedStringLength)
            write(bytes)
        }
    }
}

private fun Sink.encodeListSize(size: UInt) {
    when {
        size <= 55u -> writeByte((OFFSET_SHORT_LIST + size).toByte())

        else -> {
            val encodedStringLength = toByteArray(size.toInt())
            writeByte(((OFFSET_SHORT_LIST + 55u + encodedStringLength.size.toUInt()).toByte()))
            write(encodedStringLength)
        }
    }
}

private fun toByteArray(value: Int): ByteString = Buffer().apply {
    var started = false

    var byte = value shr 24 and 0xff
    if (byte != 0) {
        writeByte(byte.toByte())
        started = true
    }

    byte = value shr 16 and 0xff
    if (started || byte != 0) {
        writeByte(byte.toByte())
        started = true
    }

    byte = value shr 8 and 0xff
    if (started || byte != 0) {
        writeByte(byte.toByte())
        started = true
    }

    byte = value and 0xff
    if (started || byte != 0) {
        writeByte(byte.toByte())
    }
}.readByteString()
