package kosh.libs.keystone.cbor

import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString
import kotlinx.io.write
import kotlinx.io.writeString
import kotlinx.io.writeUByte
import kotlinx.io.writeUInt
import kotlinx.io.writeULong
import kotlinx.io.writeUShort

internal fun CborElement.encode(): ByteString = Buffer().run {
    encode(this@encode)
    readByteString()
}

internal fun Sink.encode(cbor: CborElement) {
    when (cbor) {
        is CborElement.CborBoolean -> encode(cbor)
        is CborElement.CborByteString -> encode(cbor)
        is CborElement.CborTextString -> encode(cbor)
        is CborElement.CborArray -> encode(cbor)
        is CborElement.CborMap -> encode(cbor)
        is CborElement.CborTagged -> encode(cbor)
        is CborElement.CborUInt -> encode(cbor)
        CborElement.CborNull -> encodeNull()
        CborElement.CborUndefined -> encodeUndefined()
    }
}

internal fun Sink.encode(cbor: CborElement.CborUInt) {
    encode(Tag.TYPE_UNSIGNED_INTEGER, cbor.value)
}

internal fun Sink.encode(cbor: CborElement.CborBoolean) {
    encode(Tag.TYPE_FLOAT_SIMPLE, (if (cbor.value) TRUE else FALSE).toULong())
}

internal fun Sink.encode(cbor: CborElement.CborArray) {
    encode(Tag.TYPE_ARRAY, cbor.size.toULong())
    for (element in cbor) {
        encode(element)
    }
}

internal fun Sink.encode(cbor: CborElement.CborMap) {
    encode(Tag.TYPE_MAP, cbor.size.toULong())
    for ((key, value) in cbor) {
        encode(key)
        encode(value)
    }
}

internal fun Sink.encode(cbor: CborElement.CborTagged) {
    encode(Tag.TYPE_TAG, cbor.tag)
    encode(cbor.value)
}

internal fun Sink.encodeNull() {
    encode(Tag.TYPE_FLOAT_SIMPLE, NULL.toULong())
}

internal fun Sink.encodeUndefined() {
    encode(Tag.TYPE_FLOAT_SIMPLE, UNDEFINED.toULong())
}

internal fun Sink.encode(cbor: CborElement.CborByteString) {
    encode(Tag.TYPE_BYTE_STRING, cbor.value.size.toULong())
    write(cbor.value)
}

internal fun Sink.encode(cbor: CborElement.CborTextString) {
    encode(Tag.TYPE_TEXT_STRING, cbor.value.length.toULong())
    writeString(cbor.value)
}

private fun Sink.encode(tag: Tag, value: ULong) {
    val tagNumber = tag.toNumber() shl 5

    if (value < ONE_BYTE) {
        writeUByte((tagNumber or value.toUInt()).toUByte())
    } else if (value <= UByte.MAX_VALUE) {
        writeUByte((tagNumber or ONE_BYTE).toUByte())
        writeUByte(value.toUByte())
    } else if (value <= UShort.MAX_VALUE) {
        writeUByte((tagNumber or TWO_BYTES).toUByte())
        writeUShort(value.toUShort())
    } else if (value <= UInt.MAX_VALUE) {
        writeUByte((tagNumber or FOUR_BYTES).toUByte())
        writeUInt(value.toUInt())
    } else {
        writeUByte((tagNumber or EIGHT_BYTES).toUByte())
        writeULong(value)
    }
}

private fun Tag.toNumber() = when (this) {
    Tag.TYPE_UNSIGNED_INTEGER -> TYPE_UNSIGNED_INTEGER
    Tag.TYPE_NEGATIVE_INTEGER -> TYPE_NEGATIVE_INTEGER
    Tag.TYPE_BYTE_STRING -> TYPE_BYTE_STRING
    Tag.TYPE_TEXT_STRING -> TYPE_TEXT_STRING
    Tag.TYPE_ARRAY -> TYPE_ARRAY
    Tag.TYPE_MAP -> TYPE_MAP
    Tag.TYPE_TAG -> TYPE_TAG
    Tag.TYPE_FLOAT_SIMPLE -> TYPE_FLOAT_SIMPLE
}.toUInt()
