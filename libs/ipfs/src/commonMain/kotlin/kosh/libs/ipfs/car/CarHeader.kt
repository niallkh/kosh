package kosh.libs.ipfs.car

import kosh.libs.ipfs.cbor.CborReader
import kosh.libs.ipfs.cid.Cid
import kosh.libs.ipfs.readVarInt
import okio.BufferedSource

internal data class CarHeader(
    val version: Int,
    val roots: List<Cid>,
)

internal object CarHeaderCborAdapter {

    internal fun decode(reader: CborReader): CarHeader {
        var version: Int = -1
        val roots = mutableListOf<Cid>()
        reader.forEachKey {
            when (it) {
                "version" -> version = reader.readUInt().toInt()
                "roots" -> reader.readArray {
                    roots.add(reader.readCid())
                }
            }
        }
        return CarHeader(version = version, roots = roots)
    }
}

internal fun BufferedSource.readCarHeader(): CarHeader {
    val size = readVarInt()
    require(size.toLong())
    val cborReader = CborReader(this)
    return CarHeaderCborAdapter.decode(cborReader)
}
