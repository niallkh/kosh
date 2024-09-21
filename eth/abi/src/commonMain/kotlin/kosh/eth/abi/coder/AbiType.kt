package kosh.eth.abi.coder

import kosh.eth.abi.dsl.AbiTupleDsl

public sealed interface AbiType {

    public val isDynamic: Boolean

    public data class UInt(val bitSize: kotlin.UInt) : AbiType {

        override val isDynamic: Boolean
            get() = false

        override fun toString(): String = "uint$bitSize"

        public companion object {
            public val UInt256: UInt = UInt(256u)
            public val UInt192: UInt = UInt(192u)
            public val UInt64: UInt = UInt(64u)
            public val UInt32: UInt = UInt(32u)
            public val UInt16: UInt = UInt(16u)
            public val UInt8: UInt = UInt(8u)
        }
    }

    public data class Int(val bitSize: kotlin.UInt) : AbiType {

        override val isDynamic: Boolean
            get() = false

        override fun toString(): String = "int$bitSize"

        public companion object {
            public val Int256: Int = Int(256u)
        }
    }

    public data object Bool : AbiType {

        override val isDynamic: Boolean
            get() = false

        override fun toString(): String = "bool"
    }

    public data class FixedBytes(
        val byteSize: kotlin.UInt,
    ) : AbiType {

        override val isDynamic: Boolean
            get() = false

        override fun toString(): String = "bytes$byteSize"

        public companion object {
            public val Bytes32: FixedBytes = FixedBytes(32u)
            public val Bytes24: FixedBytes = FixedBytes(24u)
            public val Bytes4: FixedBytes = FixedBytes(4u)
        }
    }

    public data object Address : AbiType {

        override val isDynamic: Boolean
            get() = false

        override fun toString(): String = "address"
    }

    public data object Function : AbiType {

        override val isDynamic: Boolean
            get() = false

        override fun toString(): String = "function"
    }

    public data object DynamicString : AbiType {

        override val isDynamic: Boolean
            get() = true

        override fun toString(): String = "string"
    }

    public data object DynamicBytes : AbiType {

        override val isDynamic: Boolean
            get() = true

        override fun toString(): String = "bytes"
    }

    public data class DynamicArray(
        public val type: AbiType,
    ) : AbiType {

        override val isDynamic: Boolean
            get() = true

        override fun toString(): String = "$type[]"
    }

    public data class FixedArray(
        val size: kotlin.UInt,
        val type: AbiType,
    ) : AbiType {

        init {
            require(size >= 1u)
        }

        override val isDynamic: Boolean
            get() = type.isDynamic

        override fun toString(): String = "$type[$size]"
    }

    public data class Tuple(
        val name: String? = null,
        private val parameters: List<Parameter> = emptyList(),
    ) : AbiType, List<Tuple.Parameter> by parameters {

        override val isDynamic: Boolean
            get() = parameters.any { it.type.isDynamic }

        override fun toString(): String {
            return parameters.joinToString(prefix = "(", separator = ",", postfix = ")") { param ->
                "${param.type}"
            }
        }

        public data class Parameter(
            val type: AbiType,
            val name: String,
            val indexed: Boolean = false,
        )
    }

    public companion object {
        public inline fun tuple(
            name: String? = null,
            types: AbiTupleDsl.() -> Unit,
        ): Tuple = Tuple(name = name, AbiTupleDsl().apply(types).parameters)
    }
}
