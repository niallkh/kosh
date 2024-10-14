package kosh.eth.abi.json

import kosh.eth.abi.abi
import kosh.eth.abi.address
import kosh.eth.abi.eip712.Eip712
import kosh.eth.abi.eip712.Eip712Type
import kosh.eth.abi.eip712.Eip712Type.Tuple.Parameter
import kosh.eth.abi.eip712.decodeTuple
import kotlinx.io.bytestring.hexToByteString
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

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
        public fun from(text: String): JsonEip712 = json.decodeFromString<JsonEip712>(text)
    }
}

internal fun JsonEip712.toEip712(): Eip712 {
    val eip712Types = types.map { (name, params) ->
        Eip712Type.Tuple(name) to params.map {
            Parameter(it.name, toType(it.type))
        }
    }.toMap()

    val entryType = Eip712Type.Tuple(primaryType)

    val eip712Domain = Eip712.Domain(
        name = domain.name?.abi,
        version = domain.version?.abi,
        chainId = domain.chainId?.abi,
        verifyingContract = domain.verifyingContract?.removePrefix("0x")
            ?.hexToByteString()?.abi?.address,
        salt = domain.salt?.removePrefix("0x")?.hexToByteString()?.abi
    )

    return Eip712(
        types = eip712Types,
        primaryType = entryType,
        domain = eip712Domain,
        message = message.decodeTuple(eip712Types, entryType),
    )
}

private fun toType(type: String): Eip712Type {
    val arrays = arrayRegex.findAll(type).toMutableList()
    val typeName = arrays.removeFirst().value

    return arrays.fold(typeOf(typeName)) { t, arr ->
        arrayTypeOf(arr.value, t)
    }
}

private fun typeOf(typeName: String): Eip712Type = when {
    typeName == "address" -> Eip712Type.Address
    typeName == "bool" -> Eip712Type.Bool
    typeName == "string" -> Eip712Type.DynamicString

    typeName.startsWith("uint") -> typeName.removePrefix("uint").toUIntOrNull()
        ?.let { Eip712Type.UInt(it) }
        ?: error("Invalid UInt type")

    typeName.startsWith("int") -> typeName.removePrefix("int").toUIntOrNull()
        ?.let { Eip712Type.Int(it) }
        ?: error("Invalid Int type")

    typeName.startsWith("bytes") -> typeName.removePrefix("bytes").toUIntOrNull()
        ?.let { Eip712Type.FixedBytes(it) }
        ?: Eip712Type.DynamicBytes

    else -> Eip712Type.Tuple(typeName)
}

private fun arrayTypeOf(
    arr: String,
    childType: Eip712Type,
): Eip712Type {
    val size = arr.removePrefix("[").removeSuffix("]").toUIntOrNull()
    return if (size != null) Eip712Type.FixedArray(size, childType) else Eip712Type.DynamicArray(
        childType
    )
}
