package kosh.libs.ipfs

import okio.BufferedSink
import okio.BufferedSource

private const val ZERO: UByte = 0u

internal fun BufferedSource.readVarInt(): ULong {
    var shift = 0
    var result = 0uL

    if (exhausted()) return result

    while (shift < 64) {
        val current = readByte().toUByte()
        result = result or current.and(0b0111_1111u).toULong().shl(shift)
        if (current.and(0b1000_0000U) == ZERO || exhausted()) return result
        shift += 7
    }

    throw IllegalArgumentException("VarInt too long")
}

internal fun BufferedSink.writeVarUInt(varInt: ULong) {
    var value = varInt
    while (value and 0b0111_1111uL.inv() != 0uL) {
        writeByte((value.toInt() and 0b0111_1111) or 0b1000_0000)
        value = value shr 7
    }
    writeByte(value.toInt())
}
