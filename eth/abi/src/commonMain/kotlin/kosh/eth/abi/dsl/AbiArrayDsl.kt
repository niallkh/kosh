package kosh.eth.abi.dsl

import kosh.eth.abi.Type

@AbiDsl
public class AbiArrayDsl {
    internal lateinit var type: Type

    private fun set(abiType: Type) {
        require(::type.isInitialized.not())
        type = abiType
    }

    @AbiDsl
    public fun uint256() {
        set(Type.UInt.UInt256)
    }

    @AbiDsl
    public fun uint32() {
        set(Type.UInt.UInt32)
    }

    @AbiDsl
    public fun uint8() {
        set(Type.UInt.UInt8)
    }

    @AbiDsl
    public fun int256() {
        set(Type.Int.Int256)
    }

    @AbiDsl
    public fun bytes4() {
        set(Type.FixedBytes.Bytes4)
    }

    @AbiDsl
    public fun bytes32() {
        set(Type.FixedBytes.Bytes32)
    }

    @AbiDsl
    public fun bytes(size: UInt? = null) {
        if (size == null) {
            set(Type.DynamicBytes)
        } else {
            set(Type.FixedBytes(size))
        }
    }

    @AbiDsl
    public fun address() {
        set(Type.Address)
    }

    @AbiDsl
    public fun string() {
        set(Type.DynamicString)
    }

    @AbiDsl
    public fun bool() {
        set(Type.Bool)
    }

    @AbiDsl
    public fun tuple(
        typeName: String? = null,
        @AbiDsl block: AbiTupleDsl.() -> Unit,
    ) {
        val params = AbiTupleDsl().apply(block)
        set(Type.Tuple(typeName, params.parameters))
    }

    @AbiDsl
    public fun array(
        size: UInt? = null,
        @AbiDsl block: AbiArrayDsl.() -> Unit,
    ) {
        val type = AbiArrayDsl().apply(block)
        if (size == null) {
            set(Type.DynamicArray(type.type))
        } else {
            set(Type.FixedArray(size = size, type = type.type))
        }
    }
}
