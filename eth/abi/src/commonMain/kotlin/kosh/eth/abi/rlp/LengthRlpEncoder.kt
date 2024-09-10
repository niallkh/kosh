package kosh.eth.abi.rlp

import okio.ByteString

public class LengthRlpEncoder : RlpEncoder {

    public var length: Int = 0

    override fun string(string: RlpType.RlpString) {
        length += lengthString(string.bytes)
    }

    override fun list(list: RlpType.RlpList) {
        if (list.isEmpty()) {
            length += lengthList(0)
        } else {
            val encoder = LengthRlpEncoder()
            for (entry in list) {
                entry.encode(encoder)
            }
            length += lengthList(encoder.length)
        }
    }

    private fun lengthString(bytes: ByteString): Int {
        return when {
            bytes.size == 1
                    && bytes[0] in 0x00 until OFFSET_SHORT_STRING -> bytes.size

            bytes.size <= 55 -> bytes.size + 1
            else -> 1 + minimalByteArraySize(bytes.size) + bytes.size
        }
    }

    private fun lengthList(byteSize: Int): Int = when {
        byteSize <= 55 -> byteSize + 1
        else -> 1 + minimalByteArraySize(byteSize) + byteSize
    }

    private fun minimalByteArraySize(value: Int): Int {
        return 4 - value.countLeadingZeroBits() / 8
    }
}
