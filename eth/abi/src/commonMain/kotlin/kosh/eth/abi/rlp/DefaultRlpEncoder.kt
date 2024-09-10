package kosh.eth.abi.rlp

import okio.Buffer
import okio.ByteString

public class DefaultRlpEncoder(
    private val buffer: Buffer,
) : RlpEncoder {

    override fun string(string: RlpType.RlpString) {
        encodeString(string.bytes)
    }

    override fun list(list: RlpType.RlpList) {
        if (list.isEmpty()) {
            encodeListSize(0)
        } else {
            val lengthRlpEncoder = LengthRlpEncoder()
            for (entry in list) {
                entry.encode(lengthRlpEncoder)
            }
            encodeListSize(lengthRlpEncoder.length)

            for (entry in list) {
                entry.encode(this)
            }
        }
    }

    private fun encodeString(bytes: ByteString) {
        when {
            bytes.size == 1
                    && bytes[0] in 0x00 until OFFSET_SHORT_STRING -> buffer.write(bytes)

            bytes.size <= 55 -> {
                buffer.writeByte(OFFSET_SHORT_STRING + bytes.size)
                buffer.write(bytes)
            }

            else -> {
                val encodedStringLength = toByteArray(bytes.size)
                buffer.writeByte((OFFSET_SHORT_STRING + 55 + encodedStringLength.size))
                buffer.write(encodedStringLength)
                buffer.write(bytes)
            }
        }
    }

    private fun encodeListSize(size: Int) {
        when {
            size <= 55 -> buffer.writeByte(OFFSET_SHORT_LIST + size)

            else -> {
                val encodedStringLength = toByteArray(size)
                buffer.writeByte((OFFSET_SHORT_LIST + 55 + encodedStringLength.size))
                buffer.write(encodedStringLength)
            }
        }
    }

    private fun toByteArray(value: Int): ByteString = Buffer().apply {
        var started = false

        var byte = value shr 24 and 0xff
        if (byte != 0) {
            writeByte(byte)
            started = true
        }

        byte = value shr 16 and 0xff
        if (started || byte != 0) {
            writeByte(byte)
            started = true
        }

        byte = value shr 8 and 0xff
        if (started || byte != 0) {
            writeByte(byte)
            started = true
        }

        byte = value and 0xff
        if (started || byte != 0) {
            writeByte(byte)
        }
    }.readByteString()
}
