package kosh.libs.ipfs.base

import okio.Buffer
import okio.ByteString

private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
private const val URL_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"
private const val PAD = '='

internal fun String.decodeBase64(): ByteString = decodeBase64Internal()

internal fun String.decodeBase64OrNull(): ByteString? = try {
    decodeBase64Internal()
} catch (e: IllegalArgumentException) {
    null
}

internal fun ByteString.encodeBase64(
    url: Boolean = false,
    pads: Boolean = false,
): String = encodeBase64Internal(url = url, pads = pads)


private fun ByteString.encodeBase64Internal(
    url: Boolean = false,
    pads: Boolean = false,
): String {
    val map: String = if (url) URL_ALPHABET else ALPHABET

    val capacity = ((size + 2) / 3) * 4
    return buildString(capacity) {
        var word = 0
        var modulus = 0

        repeat(size) {
            modulus = (modulus + 1) % 3
            word = (word shl 8) or (this@encodeBase64Internal[it].toInt() and 0xFF)

            if (modulus == 0) {
                append(map[(word shr 18 and 0x3F)])
                append(map[(word shr 12 and 0x3F)])
                append(map[(word shr 6 and 0x3F)])
                append(map[(word and 0x3F)])
            }
        }

        when (modulus) {
            0 -> Unit
            1 -> {
                append(map[(word shr 2 and 0x3F)])
                append(map[(word shl 4 and 0x3F)])
                if (pads) {
                    append(PAD)
                    append(PAD)
                }
            }

            2 -> {
                append(map[(word shr 10 and 0x3F)])
                append(map[(word shr 4 and 0x3F)])
                append(map[(word shl 2 and 0x3F)])
                if (pads) {
                    append(PAD)
                }
            }

            else -> error("panic!")
        }
    }
}

private fun String.decodeBase64Internal(): ByteString {
    val stripped = dropLastWhile { it == '=' }
    val buffer = Buffer()

    var word = 0
    var modulus = 0

    repeat(stripped.length) {
        val c = stripped[it]
        modulus = (modulus + 1) % 4

        val bits = alphabet(c)
        word = word shl 6 or bits

        if (modulus == 0) {
            buffer.writeByte((word shr 16))
            buffer.writeByte((word shr 8))
            buffer.writeByte(word)
        }
    }

    when (modulus) {
        0 -> Unit
        1 -> throw IllegalArgumentException("Truncated input")
        2 -> {
            buffer.writeByte((word shr 4))
        }

        3 -> {
            buffer.writeByte((word shr 10))
            buffer.writeByte((word shr 2))
        }

        else -> error("panic!")
    }

    return buffer.readByteString()
}

@Suppress("NOTHING_TO_INLINE")
private inline fun alphabet(char: Char): Int = when (char) {
    in '0'..'9' -> char.code + 52 - 48
    in 'A'..'Z' -> char.code - 65
    in 'a'..'z' -> char.code + 26 - 97
    '+', '-' -> 62
    '/', '_' -> 63
    else -> throw IllegalArgumentException("Char not in base64: $char")
}
