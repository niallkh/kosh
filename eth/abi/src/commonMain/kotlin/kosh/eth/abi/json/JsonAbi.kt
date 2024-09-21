package kosh.eth.abi.json

import kosh.eth.abi.Abi
import kosh.eth.abi.coder.AbiType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement

internal data class JsonAbi(
    private val items: List<Item>,
) : List<JsonAbi.Item> by items {

    @Serializable
    data class Item(
        val anonymous: Boolean? = null,
        val payable: Boolean? = null,
        val constant: Boolean? = null,
        val name: String? = null,
        val type: Type,
        val stateMutability: StateMutability? = null,
        val inputs: List<Parameter> = emptyList(),
        val outputs: List<Parameter> = emptyList(),
    ) {

        @Serializable
        data class Parameter(
            val type: String,
            val name: String,
            val internalType: String? = null,
            val components: List<Parameter>? = null,
            val indexed: Boolean? = null,
        )

        @Suppress("EnumEntryName")
        @Serializable
        enum class StateMutability {
            pure,
            view,
            nonpayable,
            payable,
        }

        @Suppress("EnumEntryName")
        @Serializable
        enum class Type {
            `constructor`,
            event,
            function,
            error,
            fallback,
            receive,
        }
    }

    companion object {
        fun from(json: String): JsonAbi =
            JsonAbi(Json.decodeFromString<List<Item>>(json))

        fun from(element: JsonElement): JsonAbi =
            JsonAbi(Json.decodeFromJsonElement<List<Item>>(element))
    }
}

internal fun JsonAbi.toAbi() = Abi(map(JsonAbi.Item::toAbiItem))

private fun JsonAbi.Item.toAbiItem(): Abi.Item = when (type) {
    JsonAbi.Item.Type.constructor -> Abi.Item.Constructor(
        inputs = AbiType.Tuple(null, parameters = inputs.map { it.toTupleParameter() }),
        stateMutability = stateMutability.map()
    )

    JsonAbi.Item.Type.fallback -> Abi.Item.Fallback(
        stateMutability = stateMutability.map()
    )

    JsonAbi.Item.Type.receive -> Abi.Item.Receive(
        stateMutability = stateMutability.map()
    )

    JsonAbi.Item.Type.function -> Abi.Item.Function(
        name = requireNotNull(name),
        inputs = AbiType.Tuple(parameters = inputs.map { it.toTupleParameter() }),
        outputs = AbiType.Tuple(parameters = outputs.map { it.toTupleParameter() }),
        stateMutability = stateMutability.map(),
    )

    JsonAbi.Item.Type.event -> Abi.Item.Event(
        name = requireNotNull(name),
        inputs = AbiType.Tuple(null, inputs.map { it.toTupleParameter() }),
        anonymous = anonymous ?: false,
    )

    JsonAbi.Item.Type.error -> Abi.Item.Error(
        name = requireNotNull(name),
        inputs = AbiType.Tuple(null, inputs.map { it.toTupleParameter() })
    )
}

private fun JsonAbi.Item.StateMutability?.map() = when (this) {
    JsonAbi.Item.StateMutability.pure -> Abi.Item.StateMutability.Pure
    JsonAbi.Item.StateMutability.view -> Abi.Item.StateMutability.View
    JsonAbi.Item.StateMutability.nonpayable -> Abi.Item.StateMutability.NonPayable
    JsonAbi.Item.StateMutability.payable -> Abi.Item.StateMutability.Payable
    else -> Abi.Item.StateMutability.NonPayable
}

private fun JsonAbi.Item.Parameter.toTupleParameter() = AbiType.Tuple.Parameter(
    type = toType(),
    name = name,
    indexed = indexed ?: false,
)

private fun JsonAbi.Item.Parameter.toType(): AbiType {
    val arrays = arrayRegex.findAll(type).toMutableList()
    val typeName = arrays.removeFirst().value

    return arrays.fold(typeOf(typeName)) { type, arr ->
        arrayTypeOf(arr.value, type)
    }
}

private fun JsonAbi.Item.Parameter.typeOf(typeName: String): AbiType = when {
    typeName.startsWith("uint") -> AbiType.UInt(
        typeName.removePrefix("uint").toUIntOrNull() ?: 256u
    )

    typeName.startsWith("int") -> AbiType.Int(typeName.removePrefix("int").toUIntOrNull() ?: 256u)
    typeName == "address" -> AbiType.Address
    typeName == "bool" -> AbiType.Bool
    typeName.startsWith("bytes") -> if (typeName.length == 5) AbiType.DynamicBytes
    else AbiType.FixedBytes(typeName.removePrefix("bytes").toUInt())

    typeName == "string" -> AbiType.DynamicString
    typeName == "function" -> AbiType.Function
    typeName.startsWith("tuple") -> checkNotNull(components)
        .map(JsonAbi.Item.Parameter::toTupleParameter)
        .let { AbiType.Tuple(internalType, it) }

    else -> error("Invalid type: $typeName")
}

private fun arrayTypeOf(
    arr: String,
    childType: AbiType,
): AbiType {
    val size = arr.removePrefix("[").removeSuffix("]").toUIntOrNull()
    return if (size != null) AbiType.FixedArray(
        size,
        childType
    ) else AbiType.DynamicArray(childType)
}
