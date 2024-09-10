package kosh.eth.abi.dsl

import kosh.eth.abi.Type

@AbiDsl
public class AbiTupleDsl {
    public val parameters: MutableList<Type.Tuple.Parameter> = mutableListOf()

    @AbiDsl
    public fun uint256(name: String? = null, indexed: Boolean = false) {
        parameters += Type.Tuple.Parameter(
            type = Type.UInt.UInt256,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun uint192(name: String? = null, indexed: Boolean = false) {
        parameters += Type.Tuple.Parameter(
            type = Type.UInt.UInt192,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun uint32(name: String? = null, indexed: Boolean = false) {
        parameters += Type.Tuple.Parameter(
            type = Type.UInt.UInt32,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun uint8(name: String? = null, indexed: Boolean = false) {
        parameters += Type.Tuple.Parameter(
            type = Type.UInt.UInt8,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun int256(name: String? = null, indexed: Boolean = false) {
        parameters += Type.Tuple.Parameter(
            type = Type.Int.Int256,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun bytes4(name: String? = null, indexed: Boolean = false) {
        parameters += Type.Tuple.Parameter(
            type = Type.FixedBytes.Bytes4,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun bytes32(name: String? = null, indexed: Boolean = false) {
        parameters += Type.Tuple.Parameter(
            type = Type.FixedBytes.Bytes32,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun bytes(name: String? = null, indexed: Boolean = false, size: UInt? = null) {
        parameters += if (size == null) {
            Type.Tuple.Parameter(type = Type.DynamicBytes, indexed = indexed, name = name)
        } else {
            Type.Tuple.Parameter(type = Type.FixedBytes(size), indexed = indexed, name = name)
        }
    }

    @AbiDsl
    public fun address(name: String? = null, indexed: Boolean = false) {
        parameters += Type.Tuple.Parameter(
            type = Type.Address,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun string(name: String? = null, indexed: Boolean = false) {
        parameters += Type.Tuple.Parameter(
            type = Type.DynamicString,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun bool(name: String? = null, indexed: Boolean = false) {
        parameters += Type.Tuple.Parameter(type = Type.Bool, indexed = indexed, name = name)
    }

    @AbiDsl
    public fun tuple(
        name: String? = null,
        typeName: String? = null,
        indexed: Boolean = false,
        @AbiDsl block: AbiTupleDsl.() -> Unit,
    ) {
        this.parameters += Type.Tuple.Parameter(
            type = Type.Tuple(name = typeName, parameters = AbiTupleDsl().apply(block).parameters),
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun array(
        name: String? = null,
        indexed: Boolean = false,
        size: UInt? = null,
        @AbiDsl block: AbiArrayDsl.() -> Unit,
    ) {
        parameters += if (size == null) {
            Type.Tuple.Parameter(
                type = Type.DynamicArray(AbiArrayDsl().apply(block).type),
                indexed = indexed,
                name = name
            )
        } else {
            Type.Tuple.Parameter(
                type = Type.FixedArray(size = size, type = AbiArrayDsl().apply(block).type),
                indexed = indexed,
                name = name
            )
        }
    }
}
