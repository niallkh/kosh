package kosh.eth.abi.rlp

internal const val OFFSET_SHORT_STRING = 0x80
internal const val OFFSET_SHORT_LIST = 0xc0

public interface RlpEncoder {

    public fun string(string: RlpType.RlpString)

    public fun list(list: RlpType.RlpList)
}
