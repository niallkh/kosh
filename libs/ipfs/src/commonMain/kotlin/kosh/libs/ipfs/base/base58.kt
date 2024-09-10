package kosh.libs.ipfs.base

import okio.ByteString
import okio.ByteString.Companion.toByteString

/**
 * Copyright 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

private const val ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz"

internal fun String.decodeBase58(): ByteString = decodeBase58Internal()

internal fun String.decodeBase58OrNull(): ByteString? = try {
    decodeBase58Internal()
} catch (e: IllegalArgumentException) {
    null
}

internal fun ByteString.encodeBase58(): String = encodeBase58Internal()

private fun ByteString.encodeBase58Internal(): String {
    if (size == 0) {
        return ""
    }
    val input = toByteArray()
    // Count leading zeroes.
    var zeroCount = 0
    while (zeroCount < input.size && input[zeroCount].toInt() == 0) {
        ++zeroCount
    }
    // The actual encoding.
    val temp = CharArray(input.size * 2)
    var j = temp.size
    var startAt = zeroCount
    while (startAt < input.size) {
        val mod = divmod58(input, startAt)
        if (input[startAt].toInt() == 0) {
            ++startAt
        }
        temp[--j] = ALPHABET[mod.toInt()]
    }

    // Strip extra '1' if there are some after decoding.
    while (j < temp.size && temp[j] == ALPHABET[0]) {
        ++j
    }
    // Add as many leading '1' as there were leading zeros.
    while (--zeroCount >= 0) {
        temp[--j] = ALPHABET[0]
    }

    return temp.concatToString(j, temp.size - j)
}

private fun String.decodeBase58Internal(): ByteString {
    val input = this
    if (input.isEmpty()) {
        return ByteString.EMPTY
    }
    val input58 = ByteArray(input.length)
    // Transform the String to a base58 byte sequence
    for (i in input.indices) {
        val c = input[i]
        input58[i] = alphabet(c).toByte()
    }
    // Count leading zeroes
    var zeroCount = 0
    while (zeroCount < input58.size && input58[zeroCount].toInt() == 0) {
        ++zeroCount
    }
    // The encoding
    val temp = ByteArray(input.length)
    var j = temp.size
    var startAt = zeroCount
    while (startAt < input58.size) {
        val mod = divmod256(input58, startAt)
        if (input58[startAt].toInt() == 0) {
            ++startAt
        }
        temp[--j] = mod
    }
    // Do no add extra leading zeroes, move j to first non-null byte.
    while (j < temp.size && temp[j].toInt() == 0) {
        ++j
    }
    return temp.toByteString(offset = j - zeroCount, byteCount = temp.size - j - zeroCount)
}

private fun divmod58(number: ByteArray, startAt: Int): Byte {
    var remainder = 0
    for (i in startAt until number.size) {
        val digit256 = number[i].toInt() and 0xFF
        val temp = remainder * 256 + digit256
        number[i] = (temp / 58).toByte()
        remainder = temp % 58
    }
    return remainder.toByte()
}

private fun divmod256(number58: ByteArray, startAt: Int): Byte {
    var remainder = 0
    for (i in startAt until number58.size) {
        val digit58 = number58[i].toInt() and 0xFF
        val temp = remainder * 58 + digit58
        number58[i] = (temp / 256).toByte()
        remainder = temp % 256
    }
    return remainder.toByte()
}

private fun alphabet(char: Char): Int = when (char) {
    in '1'..'9' -> char.code + 0 - 49
    in 'A'..'H' -> char.code + 9 - 65
    in 'J'..'N' -> char.code + 17 - 74
    in 'P'..'Z' -> char.code + 22 - 80
    in 'a'..'k' -> char.code + 33 - 97
    in 'm'..'z' -> char.code + 44 - 109
    else -> throw IllegalArgumentException("Char not in base58: $char")
}
