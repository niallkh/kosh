package kosh.eth.abi.coder

import kosh.eth.abi.Abi
import kosh.eth.abi.Eip712
import kosh.eth.abi.Type
import kosh.eth.abi.Value
import kosh.eth.abi.keccak256
import kosh.eth.abi.selector
import okio.Buffer
import okio.ByteString
import okio.ByteString.Companion.decodeHex
import okio.ByteString.Companion.encodeUtf8


public fun Abi.Item.Function.encodeSignature(): String = encodeSignature(name, inputs)

public fun Abi.Item.Error.encodeSignature(): String = encodeSignature(name, inputs)

public fun Abi.Item.Event.encodeSignature(): String = encodeSignature(name, inputs)

public fun Abi.Item.Function.encodeInputs(vararg values: Value): ByteString =
    encodeInputs(name, inputs, *values)

public fun Abi.Item.Event.encodeTopics(vararg values: Value?): List<ByteString?> {
    val parameters = inputs.filter { it.indexed }
    val size = if (anonymous) inputs.size else inputs.size + 1
    require(size == values.size)

    return buildList(size) {
        if (anonymous.not()) {
            add(encodeSignature().encodeUtf8().keccak256())
        }

        for (index in parameters.indices) {
            val parameter = parameters[index]
            val value = values.getOrNull(index)

            if (value == null) {
                add(null)
                continue
            }

            if (parameter.type is Type.FixedArray ||
                parameter.type is Type.Tuple ||
                parameter.type.isDynamic
            ) {
                TODO()
            } else {
                val buffer = Buffer()
                val decoder = AbiEncoder(buffer, value)
                parameter.type.code(decoder)
                add(buffer.readByteString())
            }
        }
    }
}

public fun Abi.Item.Constructor.encodeDeploy(
    byteCode: ByteString,
    vararg values: Value,
): ByteString = Buffer().apply {
    write(byteCode)
    write(encodeData(inputs, *values))
}.readByteString()

internal fun Type.Tuple.eip712TypeHash(): ByteString {
    val encoder = Eip712TypeEncoder()
    code(encoder)
    return encoder.type()
        .encodeUtf8()
        .keccak256()
}

public fun Type.Tuple.eip712StructHash(value: Value.Tuple): ByteString {
    val buffer = Buffer()
    code(Eip712ValueEncoder(buffer, value))
    return buffer.readByteString()
}

@Deprecated("")
public fun Eip712.eip712SignHash(): ByteString {
    return Buffer().apply {
        write("1901".decodeHex())
        write(domain.type.eip712StructHash(domain.value))
        write(message.type.eip712StructHash(message.value))
    }.readByteString()
        .keccak256()
}

internal fun encodeSignature(
    name: String,
    inputs: Type.Tuple,
): String {
    val buffer = Buffer().apply {
        writeUtf8(name)
    }
    val encoder = SignatureEncoder(buffer = buffer)
    inputs.code(encoder)
    return buffer.readByteString().utf8()
}

private fun encodeInputs(
    name: String,
    inputs: Type.Tuple,
    vararg values: Value,
): ByteString {
    val functionSelector = encodeSignature(name, inputs).encodeUtf8().keccak256().selector()!!
    val buffer = Buffer()
    buffer.write(functionSelector)
    if (inputs.isNotEmpty()) {
        inputs.code(AbiEncoder(buffer, Value.tuple(*values)))
    }
    return buffer.readByteString()
}

public fun encodeData(
    tuple: Type.Tuple,
    vararg values: Value,
): ByteString {
    val buffer = Buffer()
    if (tuple.isNotEmpty()) {
        tuple.code(AbiEncoder(buffer, Value.tuple(*values)))
    }
    return buffer.readByteString()
}
