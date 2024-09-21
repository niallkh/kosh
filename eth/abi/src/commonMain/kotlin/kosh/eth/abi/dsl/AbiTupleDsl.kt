package kosh.eth.abi.dsl

import kosh.eth.abi.coder.AbiType

@AbiDsl
public class AbiTupleDsl {
    public val parameters: MutableList<AbiType.Tuple.Parameter> = mutableListOf()

    @AbiDsl
    public fun uint256(name: String, indexed: Boolean = false) {
        parameters += AbiType.Tuple.Parameter(
            type = AbiType.UInt.UInt256,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun uint192(name: String, indexed: Boolean = false) {
        parameters += AbiType.Tuple.Parameter(
            type = AbiType.UInt.UInt192,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun uint32(name: String, indexed: Boolean = false) {
        parameters += AbiType.Tuple.Parameter(
            type = AbiType.UInt.UInt32,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun uint8(name: String, indexed: Boolean = false) {
        parameters += AbiType.Tuple.Parameter(
            type = AbiType.UInt.UInt8,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun int256(name: String, indexed: Boolean = false) {
        parameters += AbiType.Tuple.Parameter(
            type = AbiType.Int.Int256,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun bytes4(name: String, indexed: Boolean = false) {
        parameters += AbiType.Tuple.Parameter(
            type = AbiType.FixedBytes.Bytes4,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun bytes32(name: String, indexed: Boolean = false) {
        parameters += AbiType.Tuple.Parameter(
            type = AbiType.FixedBytes.Bytes32,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun bytes(name: String, indexed: Boolean = false, size: UInt? = null) {
        parameters += if (size == null) {
            AbiType.Tuple.Parameter(type = AbiType.DynamicBytes, indexed = indexed, name = name)
        } else {
            AbiType.Tuple.Parameter(type = AbiType.FixedBytes(size), indexed = indexed, name = name)
        }
    }

    @AbiDsl
    public fun address(name: String, indexed: Boolean = false) {
        parameters += AbiType.Tuple.Parameter(
            type = AbiType.Address,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun string(name: String, indexed: Boolean = false) {
        parameters += AbiType.Tuple.Parameter(
            type = AbiType.DynamicString,
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun bool(name: String, indexed: Boolean = false) {
        parameters += AbiType.Tuple.Parameter(type = AbiType.Bool, indexed = indexed, name = name)
    }

    @AbiDsl
    public fun tuple(
        name: String,
        typeName: String? = null,
        indexed: Boolean = false,
        @AbiDsl block: AbiTupleDsl.() -> Unit,
    ) {
        this.parameters += AbiType.Tuple.Parameter(
            type = AbiType.Tuple(
                name = typeName,
                parameters = AbiTupleDsl().apply(block).parameters
            ),
            indexed = indexed,
            name = name
        )
    }

    @AbiDsl
    public fun array(
        name: String,
        indexed: Boolean = false,
        size: UInt? = null,
        @AbiDsl block: AbiArrayDsl.() -> Unit,
    ) {
        parameters += if (size == null) {
            AbiType.Tuple.Parameter(
                type = AbiType.DynamicArray(AbiArrayDsl().apply(block).type),
                indexed = indexed,
                name = name
            )
        } else {
            AbiType.Tuple.Parameter(
                type = AbiType.FixedArray(size = size, type = AbiArrayDsl().apply(block).type),
                indexed = indexed,
                name = name
            )
        }
    }
}
