package kosh.eth.abi.json

import kosh.eth.abi.Type

internal val arrayRegex = "(\\w+|\\[\\d*])".toRegex()

internal fun arrayTypeOf(
    arr: String,
    childType: Type,
): Type {
    val size = arr.removePrefix("[").removeSuffix("]").toUIntOrNull()
    return if (size != null) Type.FixedArray(size, childType) else Type.DynamicArray(childType)
}
