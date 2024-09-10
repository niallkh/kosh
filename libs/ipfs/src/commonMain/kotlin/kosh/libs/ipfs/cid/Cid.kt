package kosh.libs.ipfs.cid

import kosh.libs.ipfs.base.decodeBase16
import kosh.libs.ipfs.base.decodeBase32
import kosh.libs.ipfs.base.decodeBase58
import kosh.libs.ipfs.base.decodeBase64
import kosh.libs.ipfs.base.encodeBase32
import kosh.libs.ipfs.readVarInt
import kosh.libs.ipfs.writeVarUInt
import okio.Buffer
import okio.BufferedSink
import okio.BufferedSource
import okio.ByteString

public data class Cid(
    internal val codec: Codec,
    internal val hash: MultiHash,
) {

    override fun toString(): String = encode()
}

internal fun cidV0(hash: ByteString) = Cid(codec = Codec.DagProtobuf, hash = multiHashV0(hash))

public fun String.decodeCidOrNull(): Cid? = try {
    decodeCid()
} catch (e: IllegalArgumentException) {
    null
}

public fun String.decodeCid(): Cid = if (length == 46 && startsWith("Qm")) {
    val bytes = decodeBase58()
    cidV0(bytes.substring(beginIndex = 2))
} else {
    require(isNotBlank()) { "String is blank" }
    val encoded = substring(startIndex = 1)

    val decoded = when {
        startsWith("f", ignoreCase = true) -> encoded.decodeBase16()
        startsWith("v", ignoreCase = true) -> encoded.decodeBase32(hex = true)
        startsWith("t", ignoreCase = true) -> encoded.decodeBase32(hex = true)
        startsWith("c", ignoreCase = true) -> encoded.decodeBase32()
        startsWith("b", ignoreCase = true) -> encoded.decodeBase32()
        startsWith("z") -> encoded.decodeBase58()
        startsWith("m") -> encoded.decodeBase64()
        startsWith("u", ignoreCase = true) -> encoded.decodeBase64()
        else -> throw IllegalArgumentException("Unknown cid base: $this")
    }

    decoded.decodeCid()
}

public fun Cid.encode(): String {
    val buffer = Buffer()
    buffer.writeCid(this@encode)
    val encoded = buffer.readByteString()
        .encodeBase32()
    return "b$encoded"
}

public fun ByteString.decodeCid(): Cid {
    return if (size == 34 && get(0).toInt() == 0x12 && get(1).toInt() == 0x20) {
        cidV0(substring(beginIndex = 2))
    } else {
        require(size > 0) { "Empty byte string" }
        Buffer().write(this@decodeCid).readCid()
    }
}

internal fun BufferedSource.readCid(): Cid {
    val peek = peek()
    val hashType = peek.readByte().toUByte().toInt()
    val hashSize = peek.readByte().toUByte().toInt()
    return if (hashType == 0x12 && hashSize == 0x20) {
        cidV0(readByteString(34).substring(beginIndex = 2))
    } else {
        val version = buffer.readVarInt()
        require(version.toInt() == 1) { "Unsupported Cid version: $version" }
        val codecType = buffer.readVarInt()
        val codec = Codec.entries.find { it.type == codecType.toLong() }
        requireNotNull(codec) { "Codec not found: $codecType" }
        val hash = buffer.readMultiHash()
        Cid(codec = codec, hash = hash)
    }
}

internal fun BufferedSink.writeCid(cid: Cid) {
    writeVarUInt(1uL)
    writeVarUInt(cid.codec.type.toULong())
    writeMultiHash(cid.hash)
}
