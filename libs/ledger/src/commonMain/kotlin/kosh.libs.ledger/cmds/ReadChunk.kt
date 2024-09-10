package kosh.libs.ledger.cmds

import okio.BufferedSource
import okio.ByteString

internal fun BufferedSource.readChunk(limit: Long): ByteString = if (request(limit)) {
    readByteString(limit)
} else {
    readByteString()
}
