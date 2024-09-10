package kosh.eth.abi.coder

import kosh.eth.abi.Type

public interface Coder {

    public fun uint(type: Type.UInt)

    public fun int(type: Type.Int)

    public fun bool(type: Type.Bool)

    public fun address(type: Type.Address)

    public fun fixedBytes(type: Type.FixedBytes)

    public fun function(type: Type.Function)

    public fun dynamicBytes(type: Type.DynamicBytes)

    public fun dynamicString(type: Type.DynamicString)

    public fun dynamicArray(type: Type.DynamicArray)

    public fun fixedArray(type: Type.FixedArray)

    public fun tuple(tuple: Type.Tuple)
}

