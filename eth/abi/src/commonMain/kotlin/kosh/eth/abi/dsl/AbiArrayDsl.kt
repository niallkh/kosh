package kosh.eth.abi.dsl

import kosh.eth.abi.coder.AbiType

@AbiDsl
public class AbiArrayDsl {
    internal lateinit var type: AbiType

    private fun set(abiType: AbiType) {
        require(::type.isInitialized.not())
        type = abiType
    }

    @AbiDsl
    public fun uint256() {
        set(AbiType.UInt.UInt256)
    }

    @AbiDsl
    public fun uint32() {
        set(AbiType.UInt.UInt32)
    }

    @AbiDsl
    public fun uint8() {
        set(AbiType.UInt.UInt8)
    }

    @AbiDsl
    public fun int256() {
        set(AbiType.Int.Int256)
    }

    @AbiDsl
    public fun bytes4() {
        set(AbiType.FixedBytes.Bytes4)
    }

    @AbiDsl
    public fun bytes32() {
        set(AbiType.FixedBytes.Bytes32)
    }

    @AbiDsl
    public fun bytes(size: UInt? = null) {
        if (size == null) {
            set(AbiType.DynamicBytes)
        } else {
            set(AbiType.FixedBytes(size))
        }
    }

    @AbiDsl
    public fun address() {
        set(AbiType.Address)
    }

    @AbiDsl
    public fun string() {
        set(AbiType.DynamicString)
    }

    @AbiDsl
    public fun bool() {
        set(AbiType.Bool)
    }

    @AbiDsl
    public fun tuple(
        typeName: String? = null,
        @AbiDsl block: AbiTupleDsl.() -> Unit,
    ) {
        val params = AbiTupleDsl().apply(block)
        set(AbiType.Tuple(typeName, params.parameters))
    }

    @AbiDsl
    public fun array(
        size: UInt? = null,
        @AbiDsl block: AbiArrayDsl.() -> Unit,
    ) {
        val type = AbiArrayDsl().apply(block)
        if (size == null) {
            set(AbiType.DynamicArray(type.type))
        } else {
            set(AbiType.FixedArray(size = size, type = type.type))
        }
    }
}
