package kosh.domain.utils

import okio.Buffer
import okio.ByteString

operator fun ByteString.plus(byteString: ByteString) =
    Buffer().apply { write(this@plus); write(byteString) }.readByteString()

operator fun ByteString.plus(long: Long) =
    Buffer().apply { write(this@plus); writeLong(long) }.readByteString()
