package kosh.eth.abi

import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString
import kotlinx.io.write

public fun Sink.padding(size: Int) {
    repeat(size) {
        writeByte(0)
    }
}

public operator fun ByteString.plus(bytes: ByteString): ByteString =
    Buffer().apply { write(this@plus); write(bytes) }.readByteString()

