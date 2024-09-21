package kosh.libs.ledger.cmds

import kotlinx.io.Source
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString

internal fun Source.readChunk(limit: Int): ByteString = if (request(limit.toLong())) {
    readByteString(limit)
} else {
    readByteString()
}
