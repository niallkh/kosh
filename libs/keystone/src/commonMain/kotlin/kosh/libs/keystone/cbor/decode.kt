package kosh.libs.keystone.cbor

import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString
import kotlinx.io.readString
import kotlinx.io.readUByte
import kotlinx.io.write

internal fun ByteString.decodeCborElement(): CborElement = Buffer().run {
    write(this@decodeCborElement)
    decodeCborElement()
}

internal fun Source.decodeCborElement(): CborElement = when (peekTag()) {
    Tag.TYPE_UNSIGNED_INTEGER -> decodeUInt()
    Tag.TYPE_NEGATIVE_INTEGER -> TODO()
    Tag.TYPE_BYTE_STRING -> decodeByteString()
    Tag.TYPE_TEXT_STRING -> decodeString()
    Tag.TYPE_ARRAY -> decodeArray()
    Tag.TYPE_MAP -> decodeMap()
    Tag.TYPE_TAG -> decodeTagged()
    Tag.TYPE_FLOAT_SIMPLE -> decodeFloatSimple()
}

internal fun Source.decodeMap(): CborElement.CborMap {
    require(Tag.TYPE_MAP)
    val size = readUInt().toInt()
    val map = buildMap(size) {
        repeat(size) {
            put(decodeCborElement(), decodeCborElement())
        }
    }
    return CborElement.CborMap(map)
}

internal fun Source.decodeArray(): CborElement.CborArray {
    require(Tag.TYPE_ARRAY)
    val size = readUInt().toInt()
    val array = buildList {
        repeat(size) {
            add(decodeCborElement())
        }
    }
    return CborElement.CborArray(array)
}

internal fun Source.decodeByteString(): CborElement.CborByteString {
    require(Tag.TYPE_BYTE_STRING)
    return CborElement.CborByteString(readByteString(readUInt().toInt()))
}

internal fun Source.decodeString(): CborElement.CborTextString {
    require(Tag.TYPE_TEXT_STRING)
    return CborElement.CborTextString(readString(readUInt().toLong()))
}

internal fun Source.decodeTagged(): CborElement.CborTagged {
    require(Tag.TYPE_TAG)
    return CborElement.CborTagged(readUInt(), decodeCborElement())
}

internal fun Source.decodeFloatSimple(): CborElement {
    require(Tag.TYPE_FLOAT_SIMPLE)

    return when (readSubType().toUInt()) {
        in 0u..19u -> TODO()
        FALSE -> CborElement.CborBoolean(false)
        TRUE -> CborElement.CborBoolean(true)
        NULL -> CborElement.CborNull
        UNDEFINED -> CborElement.CborUndefined
        HALF_PRECISION_FLOAT -> TODO()
        SINGLE_PRECISION_FLOAT -> TODO()
        DOUBLE_PRECISION_FLOAT -> TODO()
        BREAK -> TODO()
        else -> TODO()
    }
}

internal fun Source.decodeUInt(): CborElement.CborUInt {
    require(Tag.TYPE_UNSIGNED_INTEGER)
    return CborElement.CborUInt(readUInt())
}

internal fun Source.require(tag: Tag) {
    val peekTag = peekTag()
    require(peekTag == tag) { "Expected tag is $tag, but got $peekTag" }
}

internal fun Source.peekTag(): Tag {
    val byte = peek().readUByte().toUInt()
    return when (val tagNumber = byte shr 5 and 0b0000_0111u) {
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

internal fun Source.readUInt(): ULong {
    return when (val value = readSubType().toUInt()) {
        in 0u until ONE_BYTE -> value.toULong()
        ONE_BYTE -> readUInt(bytes = 1)
        TWO_BYTES -> readUInt(bytes = 2)
        FOUR_BYTES -> readUInt(bytes = 4)
        EIGHT_BYTES -> readUInt(bytes = 8)
        else -> throw IllegalArgumentException("Invalid uint: $value")
    }
}

private fun Source.readSubType(): UByte = readUByte() and 0b0001_1111u

private fun Source.readUInt(bytes: Int): ULong {
    check(bytes in 1..8)
    var result = 0uL
    repeat(bytes) {
        val byte = readUByte().toULong()
        result = result shl 8 or byte
    }
    return result
}
