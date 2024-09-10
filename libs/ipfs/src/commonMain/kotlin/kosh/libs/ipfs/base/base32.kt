package kosh.libs.ipfs.base

import okio.Buffer
import okio.ByteString

private const val ALPHABET = "abcdefghijklmnopqrstuvwxyz234567"
private const val HEX_ALPHABET = "0123456789abcdefghijklmnopqrstuv"
private const val PAD = '='

internal fun String.decodeBase32(
    hex: Boolean = false,
): ByteString = decodeBase32Internal(hex = hex)

internal fun String.decodeBase32OrNull(
    hex: Boolean = false,
): ByteString? = try {
    decodeBase32Internal(hex = hex)
} catch (e: IllegalArgumentException) {
    null
}

internal fun ByteString.encodeBase32(
    hex: Boolean = false,
    pads: Boolean = false,
): String = encodeBase32Internal(hex = hex, pads = pads)

private fun ByteString.encodeBase32Internal(
    hex: Boolean = false,
    pads: Boolean = false,
): String {
    val map: String = if (hex) HEX_ALPHABET else ALPHABET

    val capacity = ((size + 4) / 5) * 8
    return buildString(capacity) {

        var word = 0L
        var modulus = 0

        repeat(size) {
            modulus = (modulus + 1) % 5
            word = (word shl 8) or (this@encodeBase32Internal[it].toLong() and 0xFF)

            if (modulus == 0) {
                append(map[(word shr 35 and 0x1F).toInt()])
                append(map[(word shr 30 and 0x1F).toInt()])
                append(map[(word shr 25 and 0x1F).toInt()])
                append(map[(word shr 20 and 0x1F).toInt()])
                append(map[(word shr 15 and 0x1F).toInt()])
                append(map[(word shr 10 and 0x1F).toInt()])
                append(map[(word shr 5 and 0x1F).toInt()])
                append(map[(word and 0x1F).toInt()])
            }
        }

        when (modulus) {
            0 -> Unit
            1 -> {
                append(map[(word shr 3 and 0x1F).toInt()])
                append(map[(word shl 2 and 0x1F).toInt()])
                if (pads) {
                    repeat(6) {
                        append(PAD)
                    }
                }
            }

            2 -> {
                append(map[(word shr 11 and 0x1F).toInt()])
                append(map[(word shr 6 and 0x1F).toInt()])
                append(map[(word shr 1 and 0x1F).toInt()])
                append(map[(word shl 4 and 0x1F).toInt()])
                if (pads) {
                    repeat(4) {
                        append(PAD)
                    }
                }
            }

            3 -> {
                append(map[(word shr 19 and 0x1F).toInt()])
                append(map[(word shr 14 and 0x1F).toInt()])
                append(map[(word shr 9 and 0x1F).toInt()])
                append(map[(word shr 4 and 0x1F).toInt()])
                append(map[(word shl 1 and 0x1F).toInt()])
                if (pads) {
                    repeat(3) {
                        append(PAD)
                    }
                }
            }

            4 -> {
                append(map[(word shr 27 and 0x1F).toInt()])
                append(map[(word shr 22 and 0x1F).toInt()])
                append(map[(word shr 17 and 0x1F).toInt()])
                append(map[(word shr 12 and 0x1F).toInt()])
                append(map[(word shr 7 and 0x1F).toInt()])
                append(map[(word shr 2 and 0x1F).toInt()])
                append(map[(word shl 3 and 0x1F).toInt()])
                if (pads) {
                    append(PAD)
                }
            }

            else -> error("panic!")
        }
    }
}

private fun String.decodeBase32Internal(
    hex: Boolean = false,
): ByteString {
    val stripped = dropLastWhile { it == '=' }
    val buffer = Buffer()

    var word = 0L
    var modulus = 0

    repeat(stripped.length) {
        val c = stripped[it]
        modulus = (modulus + 1) % 8

        val bits = if (hex) hexAlphabet(c) else alphabet(c)
        word = word shl 5 or bits.toLong()

        if (modulus == 0) {
            buffer.writeByte((word shr 32).toInt())
            buffer.writeByte((word shr 24).toInt())
            buffer.writeByte((word shr 16).toInt())
            buffer.writeByte((word shr 8).toInt())
            buffer.writeByte(word.toInt())
        }
    }

    when (modulus) {
        0 -> Unit
        1, 3, 6 -> throw IllegalArgumentException("Truncated input")
        2 -> {
            buffer.writeByte((word shr 2).toInt())
        }

        4 -> {
            buffer.writeByte((word shr 12).toInt())
            buffer.writeByte((word shr 4).toInt())
        }

        5 -> {
            buffer.writeByte((word shr 17).toInt())
            buffer.writeByte((word shr 9).toInt())
            buffer.writeByte((word shr 1).toInt())
        }

        7 -> {
            buffer.writeByte((word shr 27).toInt())
            buffer.writeByte((word shr 19).toInt())
            buffer.writeByte((word shr 11).toInt())
            buffer.writeByte((word shr 3).toInt())
        }

        else -> error("panic!")
    }

    return buffer.readByteString()
}

private fun alphabet(char: Char): Int = when (char) {
    in '2'..'9' -> char.code + 26 - 50
    in 'A'..'Z' -> char.code - 65
    in 'a'..'z' -> char.code - 97
    else -> throw IllegalArgumentException("Char not in base32: $char")
}

private fun hexAlphabet(char: Char): Int = when (char) {
    in '0'..'9' -> char.code - 48
    in 'A'..'V' -> char.code + 10 - 65
    in 'a'..'v' -> char.code + 10 - 97
    else -> throw IllegalArgumentException("Char not in base32 hex: $char")
}
