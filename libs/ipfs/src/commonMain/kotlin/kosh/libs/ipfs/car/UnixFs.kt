package kosh.libs.ipfs.car

import kosh.libs.ipfs.car.proto.Data
import kosh.libs.ipfs.cid.Cid
import okio.ByteString

internal sealed interface UnixFs {

    data class File(
        val blocks: List<Cid>,
        val blockSizes: List<Long>,
        val data: ByteString?,
        val size: Long,
    ) : UnixFs

    data class Directory(
        val childs: List<Link>,
    ) : UnixFs

    data class HamtDirectory(
        val childs: List<Link>,
    ) : UnixFs

    data class RawBytes(
        val data: ByteString,
    ) : UnixFs
}

internal fun unixFs(carData: CarData.Proto): UnixFs {
    require(carData.data != null)
    val data = Data.ADAPTER.decode(carData.data)
    return when (val type = data.type) {
        Data.DataType.File -> {
            require(carData.links.size == data.blocksizes.size)
            UnixFs.File(
                blocks = carData.links.map { it.cid },
                data = data.data_,
                size = requireNotNull(data.filesize),
                blockSizes = data.blocksizes,
            )
        }

        Data.DataType.Directory -> UnixFs.Directory(childs = carData.links)
        Data.DataType.HAMTShard -> UnixFs.HamtDirectory(childs = carData.links)
        Data.DataType.Raw -> UnixFs.RawBytes(data.data_!!)

        else -> throw IllegalArgumentException("Unsupported unix fs type: $type")
    }
}
