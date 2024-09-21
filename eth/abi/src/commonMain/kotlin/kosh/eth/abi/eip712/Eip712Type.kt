package kosh.eth.abi.eip712

public sealed interface Eip712Type {

    public data class UInt(val bitSize: kotlin.UInt) : Eip712Type {
        public companion object {
            public val UInt256: UInt = UInt(256u)
        }
    }

    public data class Int(val bitSize: kotlin.UInt) : Eip712Type {
        public companion object {
            public val Int256: Int = Int(256u)
        }
    }

    public data object Bool : Eip712Type

    public data class FixedBytes(
        val byteSize: kotlin.UInt,
    ) : Eip712Type {
        public companion object {
            public val Bytes32: FixedBytes = FixedBytes(32u)
        }
    }

    public data object Address : Eip712Type

    public data object DynamicString : Eip712Type

    public data object DynamicBytes : Eip712Type

    public data class DynamicArray(public val type: Eip712Type) : Eip712Type

    public data class FixedArray(val size: kotlin.UInt, val type: Eip712Type) : Eip712Type

    public data class Tuple(val name: String) : Eip712Type {

        public data class Parameter(
            val name: String,
            val type: Eip712Type,
        )

        public companion object {
            public val Domain: Tuple = Tuple("EIP712Domain")
        }
    }
}
