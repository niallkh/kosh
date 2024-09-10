package kosh.eth.abi.json

import kosh.eth.abi.Eip712
import kosh.eth.abi.Eip712V2
import kosh.eth.abi.Type
import kosh.eth.abi.Value
import kosh.eth.abi.abi
import kosh.eth.abi.abiAddress
import kosh.eth.abi.coder.Eip712MessageDecoder
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import okio.ByteString.Companion.decodeHex

internal typealias JsonEip712Types = Map<String, List<JsonEip712.Parameter>>

private val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
}

@Serializable
public data class JsonEip712(
    val types: JsonEip712Types,
    val domain: Domain,
    val primaryType: String,
    val message: JsonElement,
) {

    @Serializable
    public data class Domain(
        val name: String? = null,
        val version: String? = null,
        @Serializable(ChainIdSerializer::class)
        val chainId: ULong? = null,
        val verifyingContract: String? = null,
        val salt: String? = null,
    )

    @Serializable
    public data class Parameter(
        val name: String,
        val type: String,
    )

    public companion object {
        public fun from(
            text: String,
        ): JsonEip712 = json.decodeFromString<JsonEip712>(text)
    }
}

internal fun JsonEip712.toEip712() = Eip712(
    domain = Eip712.Message(
        type = types.getType("EIP712Domain"),
        value = Value.tuple(
            listOfNotNull(
                domain.name?.abi,
                domain.version?.abi,
                domain.chainId?.abi,
                domain.verifyingContract?.removePrefix("0x")?.decodeHex()?.abiAddress,
                domain.salt?.removePrefix("0x")?.decodeHex()?.abi
            )
        )
    ),
    message = types.getType(primaryType).let { type ->
        Eip712.Message(
            type = type,
            value = parseMessage(type, message)
        )
    },
)

internal fun JsonEip712.toEip712V2() = Eip712V2(
    types = types.mapValues { (name, params) ->
        Type.Tuple(
            name = name,
            parameters = params.map {
                types.toTupleParameter(it)
            }
        )
    },
    primaryType = primaryType,
    domain = Eip712V2.Domain(
        name = domain.name?.abi,
        version = domain.version?.abi,
        chainId = domain.chainId?.abi,
        verifyingContract = domain.verifyingContract?.removePrefix("0x")?.decodeHex()?.abiAddress,
        salt = domain.salt?.removePrefix("0x")?.decodeHex()?.abi
    ),
    message = parseMessage(types.getType(primaryType), message),
)

private fun parseMessage(type: Type.Tuple, message: JsonElement): Value.Tuple {
    val decoder = Eip712MessageDecoder(message)
    type.code(decoder)
    return decoder.value<Value.Tuple>()
}

private fun JsonEip712Types.getType(name: String): Type.Tuple = Type.Tuple(
    name = name,
    parameters = get(name)?.map { toTupleParameter(it) } ?: error("Undefined type $name")
)

private fun JsonEip712Types.toTupleParameter(parameter: JsonEip712.Parameter) =
    Type.Tuple.Parameter(
        name = parameter.name,
        type = toType(parameter.type)
    )

private fun JsonEip712Types.toType(type: String): Type {
    val arrays = arrayRegex.findAll(type).toMutableList()
    val typeName = arrays.removeFirst().value

    return arrays.fold(typeOf(typeName)) { t, arr ->
        arrayTypeOf(arr.value, t)
    }
}

private fun JsonEip712Types.typeOf(typeName: String): Type = when {
    typeName.startsWith("uint") -> typeName.removePrefix("uint").toUIntOrNull()
        ?.let { Type.UInt(it) }
        ?: error("Invalid uint type")

    typeName.startsWith("int") -> typeName.removePrefix("uint").toUIntOrNull()
        ?.let { Type.UInt(it) }
        ?: error("Invalid int type")

    typeName == "address" -> Type.Address
    typeName == "bool" -> Type.Bool
    typeName == "function" -> Type.Function
    typeName.startsWith("bytes") -> if (typeName.length == 5) Type.DynamicBytes
    else Type.FixedBytes(typeName.removePrefix("bytes").toUInt())

    typeName == "string" -> Type.DynamicString

    else -> get(typeName)?.let { parameters ->
        Type.Tuple(typeName, parameters.map { toTupleParameter(it) })
    } ?: error("Undefined type $typeName")
}
