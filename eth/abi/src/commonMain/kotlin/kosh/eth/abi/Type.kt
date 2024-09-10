package kosh.eth.abi

import kosh.eth.abi.coder.Coder
import kosh.eth.abi.dsl.AbiTupleDsl

public sealed interface Type {

    public val isDynamic: Boolean

    public fun code(coder: Coder)

    public data class UInt(val bitSize: kotlin.UInt) : Type {

        override val isDynamic: Boolean
            get() = false

        override fun code(coder: Coder): Unit = coder.uint(this)

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

    public data class Int(val bitSize: kotlin.UInt) : Type {

        override val isDynamic: Boolean
            get() = false

        override fun code(coder: Coder): Unit = coder.int(this)

        override fun toString(): String = "int$bitSize"

        public companion object {
            public val Int256: Int = Int(256u)
        }
    }

    public data object Bool : Type {

        override val isDynamic: Boolean
            get() = false

        override fun code(coder: Coder): Unit = coder.bool(this)

        override fun toString(): String = "bool"
    }

    public data class FixedBytes(
        val byteSize: kotlin.UInt,
    ) : Type {

        override val isDynamic: Boolean
            get() = false

        override fun code(coder: Coder): Unit = coder.fixedBytes(this)

        override fun toString(): String = "bytes$byteSize"

        public companion object {
            public val Bytes32: FixedBytes = FixedBytes(32u)
            public val Bytes24: FixedBytes = FixedBytes(24u)
            public val Bytes4: FixedBytes = FixedBytes(4u)
        }
    }

    public data object Address : Type {

        override val isDynamic: Boolean
            get() = false

        override fun code(coder: Coder): Unit = coder.address(this)

        override fun toString(): String = "address"
    }

    public data object Function : Type {

        override val isDynamic: Boolean
            get() = false

        override fun code(coder: Coder): Unit = coder.function(this)

        override fun toString(): String = "function"
    }

    public data object DynamicString : Type {

        override val isDynamic: Boolean
            get() = true

        override fun code(coder: Coder): Unit = coder.dynamicString(this)

        override fun toString(): String = "string"
    }

    public data object DynamicBytes : Type {

        override val isDynamic: Boolean
            get() = true

        override fun code(coder: Coder): Unit = coder.dynamicBytes(this)

        override fun toString(): String = "bytes"
    }

    public data class DynamicArray(
        public val type: Type,
    ) : Type {

        override val isDynamic: Boolean
            get() = true

        override fun code(coder: Coder): Unit = coder.dynamicArray(this)

        override fun toString(): String = "$type[]"
    }

    public data class FixedArray(
        val size: kotlin.UInt,
        val type: Type,
    ) : Type {

        init {
            require(size >= 1u)
        }

        override val isDynamic: Boolean by lazy { type.isDynamic }

        override fun code(coder: Coder): Unit = coder.fixedArray(this)

        override fun toString(): String = "$type[$size]"
    }

    public data class Tuple(
        val name: String? = null,
        private val parameters: List<Parameter> = emptyList(),
    ) : Type, List<Tuple.Parameter> by parameters {

        override val isDynamic: Boolean by lazy { parameters.any { it.type.isDynamic } }

        override fun code(coder: Coder): Unit = coder.tuple(this)

        override fun toString(): String {
            val params = parameters.joinToString(",") { param ->
                if (param.name != null) {
                    "${param.type} ${param.name}"
                } else {
                    "${param.type}"
                }
            }
            return "($params)"
        }

        public data class Parameter(
            val type: Type,
            val name: String? = null,
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
