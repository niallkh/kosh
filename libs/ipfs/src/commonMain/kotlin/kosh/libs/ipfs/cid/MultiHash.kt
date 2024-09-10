package kosh.libs.ipfs.cid

import kosh.libs.ipfs.readVarInt
import kosh.libs.ipfs.writeVarUInt
import okio.BufferedSink
import okio.BufferedSource
import okio.ByteString

public data class MultiHash(
    val type: Type,
    val hash: ByteString,
)

internal fun multiHashV0(hash: ByteString): MultiHash {
    require(hash.size == 32)
    return MultiHash(type = Type.sha2_256, hash = hash)
}

internal fun BufferedSource.readMultiHash(): MultiHash {
    val typeIndex = readVarInt()
    val len = readVarInt()
    val hash = readByteString(len.toLong())
    val type = Type.entries.find { it.index == typeIndex.toInt() }
    requireNotNull(type) { "Hash type not found: $typeIndex" }
    require(type.length == len.toInt()) { "Invalid length" }
    return MultiHash(type = type, hash = hash)
}

internal fun BufferedSink.writeMultiHash(hash: MultiHash) {
    writeVarUInt(hash.type.index.toULong())
    writeVarUInt(hash.hash.size.toULong())
    write(hash.hash)
}
