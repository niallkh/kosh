package kosh.libs.ipfs.car

import kosh.libs.ipfs.car.proto.PBNode
import kosh.libs.ipfs.cbor.CborReader
import kosh.libs.ipfs.cid.Cid
import kosh.libs.ipfs.cid.Codec
import kosh.libs.ipfs.cid.Type
import kosh.libs.ipfs.cid.decodeCid
import kosh.libs.ipfs.cid.readCid
import kosh.libs.ipfs.readVarInt
import okio.Buffer
import okio.BufferedSource
import okio.ByteString

private const val MAX_CAR_DATA_SIZE: ULong = 1048576uL // 1mB

internal sealed interface CarData {

    val cid: Cid

    data class Raw(
        override val cid: Cid,
        val data: ByteString,
    ) : CarData

    data class Proto(
        override val cid: Cid,
        val data: ByteString?,
        val links: List<Link>,
    ) : CarData

    data class Cbor(
        override val cid: Cid,
        val link: Cid?,
        val name: String,
    ) : CarData
}

internal object CarDataCborAdapter {

    internal fun decode(cid: Cid, reader: CborReader): CarData {
        var link: Cid? = null
        lateinit var name: String

        reader.forEachKey {
            when (it) {
                "link" -> link = reader.readCidOrNull()
                "name" -> name = reader.readTextString()
            }
        }

        return CarData.Cbor(link = link, name = name, cid = cid)
    }
}

internal fun BufferedSource.readCarData(): CarData {
    val chunkSize = readVarInt()
    require(chunkSize <= MAX_CAR_DATA_SIZE) { "Car data size more than $MAX_CAR_DATA_SIZE kB" }
    require(chunkSize.toLong())
    val chunk = readByteString(chunkSize.toLong())
    val buffer = Buffer().apply { write(chunk) }
    val cid = buffer.readCid()

    val data = buffer.readByteString()
    val hash = hash(cid = cid, data)
    require(cid.hash.hash == hash)
    return carData(cid = cid, data)
}

internal fun BufferedSource.readRawBlock(cid: Cid): CarData {
    val rawBlock = readByteString()
    val hash = hash(cid, rawBlock)
    require(cid.hash.hash == hash)
    return carData(cid, rawBlock)
}

private fun carData(
    cid: Cid,
    byteString: ByteString,
): CarData = when (val codec = cid.codec) {
    Codec.Raw -> CarData.Raw(cid = cid, data = byteString)
    Codec.DagCbor -> CarDataCborAdapter.decode(cid = cid, reader = CborReader(byteString))
    Codec.DagProtobuf -> PBNode.ADAPTER.decode(byteString).let { node ->
        CarData.Proto(
            cid = cid,
            data = node.data_,
            links = node.links.map {
                Link(
                    cid = it.hash!!.decodeCid(),
                    name = it.name!!,
                    size = it.tsize!!
                )
            }
        )
    }

    else -> throw IllegalArgumentException("Unsupported car data codec: $codec")
}

private fun hash(cid: Cid, rawBlock: ByteString): ByteString {
    val hash = when (val hashType = cid.hash.type) {
        Type.sha2_256 -> rawBlock.sha256()
        Type.sha2_512 -> rawBlock.sha512()
        Type.sha1 -> rawBlock.sha1()
        Type.md5 -> rawBlock.md5()
        else -> throw IllegalArgumentException("Unsupported hash type: $hashType")
    }
    return hash
}
