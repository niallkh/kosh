package kosh.libs.ipfs.car

import kosh.libs.ipfs.cid.Cid
import okio.Buffer
import okio.BufferedSource
import okio.Source
import okio.Timeout

internal class OldCarSource(
    private val expectedCid: Cid,
    private val source: BufferedSource,
) : Source {

    private var state = State.HEADER
    private val links = ArrayDeque<Cid>()
    private val chunk = Buffer()

    override fun read(sink: Buffer, byteCount: Long): Long {
        if (state == State.HEADER) {
            val carHeader = source.readCarHeader()
            require(carHeader.roots.size == 1) { "Car source supports car file only with 1 root" }
            require(carHeader.roots.first() == expectedCid)
            links += carHeader.roots
            state = State.CHUNK
        }

        if (state == State.CHUNK) {
            while (state == State.CHUNK && links.isNotEmpty()) {
                val currentCid = links.removeLast()
                val carData = source.readCarData()
                require(currentCid == carData.cid)
                val data = when (carData) {
                    is CarData.Raw -> carData.data
                    is CarData.Proto -> {
                        when (val file = unixFs(carData)) {
                            is UnixFs.RawBytes -> file.data
                            is UnixFs.File -> {
                                if (file.data != null) {
                                    require(file.blocks.isEmpty())
                                    file.data
                                } else {
                                    require(file.blocks.isNotEmpty())
                                    links.addAll(file.blocks.asReversed())
                                    null
                                }
                            }

                            else -> throw IllegalArgumentException("Car Source works only with files")
                        }
                    }

                    else -> throw IllegalArgumentException("Unsupported car data format")
                }

                if (data != null) {
                    chunk.write(data)
                    state = State.DATA
                }
            }
        }

        if (state == State.DATA) {
            val count = chunk.read(sink, byteCount)
            if (chunk.exhausted()) {
                state = State.CHUNK
            }
            return count
        }

        return -1
    }

    override fun timeout(): Timeout = source.timeout()

    override fun close() = source.close()

    enum class State {
        HEADER,
        CHUNK,
        DATA,
    }
}
