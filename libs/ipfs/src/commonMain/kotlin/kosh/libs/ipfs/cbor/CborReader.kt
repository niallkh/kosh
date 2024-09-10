package kosh.libs.ipfs.cbor

import kosh.libs.ipfs.cid.Cid
import kosh.libs.ipfs.cid.decodeCid
import okio.Buffer
import okio.BufferedSource
import okio.ByteString

internal class CborReader(
    private val source: BufferedSource,
) {

    constructor(source: ByteString) : this(Buffer().write(source))

    fun forEachKey(handler: (String) -> Any) {
        require(Tag.TYPE_MAP)
        val size = readUInt()
        repeat(size.toInt()) {
            require(Tag.TYPE_TEXT_STRING)
            val key = readTextString()
            handler(key)
        }
    }

    fun readArray(handler: () -> Any) {
        require(Tag.TYPE_ARRAY)
        val size = readUInt()
        repeat(size.toInt()) {
            handler()
        }
    }

    fun readCidOrNull(): Cid? {
        return when (val tag = peekTag()) {
            Tag.TYPE_TAG -> readCid()
            Tag.TYPE_FLOAT_SIMPLE -> {
                readNull()
                null
            }

            else -> throw IllegalArgumentException("Unexpected tag: $tag")
        }
    }

    fun readCid(): Cid {
        require(Tag.TYPE_TAG)
        require(readUInt() == 42L)
        val byteString = readByteString()
        require(byteString[0].toInt() == 0)
        return byteString.substring(beginIndex = 1).decodeCid()
    }

    fun readNull() {
        require(Tag.TYPE_FLOAT_SIMPLE)
        require(readSubType() == NULL)
    }

    fun readByteString(): ByteString {
        require(Tag.TYPE_BYTE_STRING)
        return source.readByteString(readUInt())
    }

    fun readTextString(): String {
        require(Tag.TYPE_TEXT_STRING)
        return source.readByteString(readUInt()).utf8()
    }

    fun readUInt(): Long {
        return when (val value = readSubType()) {
            in 0 until ONE_BYTE -> value.toLong()
            ONE_BYTE -> readUInt(bytes = 1)
            TWO_BYTES -> readUInt(bytes = 2)
            FOUR_BYTES -> readUInt(bytes = 4)
            EIGHT_BYTES -> readUInt(bytes = 8)
            else -> throw IllegalArgumentException("Invalid uint: $value")
        }
    }

    private fun readSubType() = (source.readByte().toUByte().toLong() and 0b0001_1111).toInt()

    private fun require(tag: Tag) {
        val peekTag = peekTag()
        require(peekTag == tag) { "Expected tag is $tag, but got $peekTag" }
    }

    private fun readUInt(bytes: Int): Long {
        check(bytes in 1..8)
        var result = 0L
        repeat(bytes) {
            val byte = source.readByte().toUByte().toLong()
            result = result shl 8 or byte
        }
        return result
    }

    fun peekTag(): Tag {
        val byte = source.peek().readByte().toUByte().toInt()
        return when (val tagNumber = byte ushr 5 and 0b0000_0111) {
            TYPE_UNSIGNED_INTEGER -> Tag.TYPE_UNSIGNED_INTEGER
            TYPE_NEGATIVE_INTEGER -> Tag.TYPE_NEGATIVE_INTEGER
            TYPE_BYTE_STRING -> Tag.TYPE_BYTE_STRING
            TYPE_TEXT_STRING -> Tag.TYPE_TEXT_STRING
            TYPE_ARRAY -> Tag.TYPE_ARRAY
            TYPE_MAP -> Tag.TYPE_MAP
            TYPE_TAG -> Tag.TYPE_TAG
            TYPE_FLOAT_SIMPLE -> Tag.TYPE_FLOAT_SIMPLE
            else -> throw IllegalArgumentException("Invalid tag: $tagNumber")
        }
    }
}
