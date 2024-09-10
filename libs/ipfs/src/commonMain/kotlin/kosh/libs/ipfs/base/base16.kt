package kosh.libs.ipfs.base

import okio.Buffer
import okio.ByteString

private const val ALPHABET = "0123456789abcdef"

internal fun String.decodeBase16(): ByteString = decodeBase16Internal()

internal fun String.decodeBase16OrNull(): ByteString? = try {
    decodeBase16Internal()
} catch (e: IllegalArgumentException) {
    null
}

internal fun String.decodeHex(): ByteString = decodeBase16()

internal fun String.decodeHexOrNull(): ByteString? = decodeBase16OrNull()


internal fun ByteString.encodeBase16(
    prefix: Boolean = false,
): String = encodeBase16Internal(prefix = prefix)


internal fun ByteString.encodeHex(
    prefix: Boolean = false,
): String = encodeBase16Internal(prefix = prefix)


private fun ByteString.encodeBase16Internal(
    prefix: Boolean = false,
): String {
    val prefixSize = if (prefix) 2 else 0
    return buildString(capacity = prefixSize + size) {
        if (prefix) {
            append("0x")
        }
        repeat(size) {
            val byte = this@encodeBase16Internal[it].toInt()
            append(ALPHABET[byte shr 4 and 0x0F])
            append(ALPHABET[byte and 0x0F])
        }
    }
}

private fun String.decodeBase16Internal(): ByteString {
    require(length % 2 == 0) { "Length of hex is odd" }
    val stripped = removePrefix("0x").removePrefix("0X")
    val buffer = Buffer()

    for (i in stripped.indices step 2) {
        val d1 = alphabet(stripped[i]) shl 4
        val d2 = alphabet(stripped[i + 1])
        buffer.writeByte(d1 + d2)
    }

    return buffer.readByteString()
}

@Suppress("NOTHING_TO_INLINE")
private inline fun alphabet(char: Char): Int = when (char) {
    in '0'..'9' -> char.code - 48
    in 'a'..'f' -> char.code + 10 - 97
    in 'A'..'F' -> char.code + 10 - 65
    else -> throw IllegalArgumentException("Char not in hex: $char")
}

