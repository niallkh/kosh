package kosh.eth.abi.rlp

import kotlinx.io.bytestring.ByteString


internal fun estimate(type: Rlp): UInt = when (type) {
    is Rlp.RlpString -> string(type)
    is Rlp.RlpList -> list(type)
}

private fun string(string: Rlp.RlpString): UInt {
    return lengthString(string.bytes)
}

private fun list(list: Rlp.RlpList): UInt {
    return if (list.isEmpty()) {
        lengthList(0u)
    } else {
        val size = list.fold(0u) { acc, type ->
            acc + estimate(type)
        }

        lengthList(size)
    }
}

private fun lengthString(bytes: ByteString): UInt {
    return when {
        bytes.size == 1
                && bytes[0].toUByte() in 0x00u until OFFSET_SHORT_STRING -> bytes.size.toUInt()

        bytes.size <= 55 -> bytes.size.toUInt() + 1u
        else -> 1u + minimalByteArraySize(bytes.size.toUInt()) + bytes.size.toUInt()
    }
}

private fun lengthList(byteSize: UInt): UInt = when {
    byteSize <= 55u -> byteSize + 1u
    else -> 1u + minimalByteArraySize(byteSize) + byteSize
}

private fun minimalByteArraySize(value: UInt): UInt {
    return 4u - value.countLeadingZeroBits().toUInt() / 8u
}

//public class LengthRlpEncoder : RlpEncoder {
//
//    public var length: Int = 0
//
//    override fun string(string: RlpType.RlpString) {
//        length += lengthString(string.bytes)
//    }
//
//    override fun list(list: RlpType.RlpList) {
//        if (list.isEmpty()) {
//            length += lengthList(0)
//        } else {
//            val encoder = LengthRlpEncoder()
//            for (entry in list) {
//                entry.encode(encoder)
//            }
//            length += lengthList(encoder.length)
//        }
//    }
//
//    private fun lengthString(bytes: ByteString): Int {
//        return when {
//            bytes.size == 1
//                    && bytes[0] in 0x00 until OFFSET_SHORT_STRING -> bytes.size
//
//            bytes.size <= 55 -> bytes.size + 1
//            else -> 1 + minimalByteArraySize(bytes.size) + bytes.size
//        }
//    }
//
//    private fun lengthList(byteSize: Int): Int = when {
//        byteSize <= 55 -> byteSize + 1
//        else -> 1 + minimalByteArraySize(byteSize) + byteSize
//    }
//
//    private fun minimalByteArraySize(value: Int): Int {
//        return 4 - value.countLeadingZeroBits() / 8
//    }
//}
