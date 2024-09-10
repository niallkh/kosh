package kosh.eth.proposals

import okio.Buffer
import okio.ByteString

public operator fun ByteString.plus(bytes: ByteString): ByteString = Buffer().apply {
    write(this@plus)
    write(bytes)
}.readByteString()
