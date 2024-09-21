package kosh.domain.utils

import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString
import kotlinx.io.write

operator fun ByteString.plus(byteString: ByteString) =
    Buffer().apply { write(this@plus); write(byteString) }.readByteString()

operator fun ByteString.plus(long: Long) =
    Buffer().apply { write(this@plus); writeLong(long) }.readByteString()
