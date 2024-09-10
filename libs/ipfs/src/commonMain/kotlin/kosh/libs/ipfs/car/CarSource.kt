package kosh.libs.ipfs.car

import kosh.libs.ipfs.cid.Cid
import kosh.libs.ipfs.murmur.MurmurHash3
import okio.Buffer
import okio.BufferedSource
import okio.Source
import okio.Timeout
import kotlin.LazyThreadSafetyMode.NONE

private val murmur3 by lazy(NONE) { MurmurHash3() }

internal class CarSource(
    path: List<String>,
    root: Cid,
    private val source: BufferedSource,
) : Source {

    private var state = State.HEADER
    private val links = ArrayDeque(listOf(root))
    private val path = ArrayDeque(path)
    private val chunk = Buffer()

    override fun read(sink: Buffer, byteCount: Long): Long {
        if (state == State.HEADER) {
            val carHeader = source.readCarHeader()
            require(carHeader.version == 1)
            state = State.CHUNK
        }

        if (state == State.CHUNK) {
            while (state == State.CHUNK && links.isNotEmpty()) {
                val currentCid = links.removeFirst()
                val carData = source.readCarData()
                require(currentCid == carData.cid)
                val data = when (carData) {
                    is CarData.Raw -> carData.data
                    is CarData.Proto -> when (val file = unixFs(carData)) {
                        is UnixFs.RawBytes -> file.data
                        is UnixFs.File -> file.data ?: run {
                            links.addAll(file.blocks)
                            null
                        }

                        is UnixFs.Directory -> {
                            val child = path.removeFirst()
                            val link = file.childs.find { it.name == child }
                                ?: error("Not found")
                            links += link.cid
                            null
                        }

                        is UnixFs.HamtDirectory -> {
                            val child = path.removeFirst()
                            val hash = murmur3.hash128x64(child.encodeToByteArray())
                                .first()
                                .toPaddedHex()

                            val link = resolveHamtDir(
                                directory = file,
                                indexPath = hash,
                                key = child,
                            ) ?: error("Not found")

                            links += link.cid

                            null
                        }

                        else -> error("Invalid UnixFs format")
                    }

                    else -> error("Invalid Car data format")
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

    private fun resolveHamtDir(
        directory: UnixFs.HamtDirectory,
        indexPath: String,
        key: String,
    ): Link? {
        var dir: UnixFs.HamtDirectory = directory
        var index = indexPath

        while (true) {
            val prefix = index.take(2)
            val link = dir.childs.find { it.name.startsWith(prefix, ignoreCase = true) }
                ?: return null

            when {
                key == link.name.drop(2) -> return link
                link.name.equals(prefix, ignoreCase = true).not() -> return null
                else -> {
                    val carData = source.readCarData()
                    require(link.cid == carData.cid)
                    val unixFs = unixFs(carData as CarData.Proto)
                    check(unixFs is UnixFs.HamtDirectory) { "Expected ipfs hamt directory" }
                    dir = unixFs
                    index = index.drop(2)
                }
            }
        }
    }
}

private fun ULong.toPaddedHex(): String = toString(16).padStart(16, '0')
