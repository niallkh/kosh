package kosh.eth.abi.coder

import kosh.eth.abi.Abi
import kosh.eth.abi.Type
import kosh.eth.abi.Value
import kosh.eth.abi.keccak256
import kosh.eth.abi.selector
import okio.Buffer
import okio.ByteString
import okio.ByteString.Companion.encodeUtf8

public fun Abi.Item.Function.decodeOutputs(
    data: ByteString,
    vararg path: String,
): Value = decodeOutputs(outputs, data, *path)

public fun Abi.Item.Function.decodeInputs(
    data: ByteString,
    vararg path: String,
): Value = decodeInputs(name, inputs, data, *path)

public fun Abi.Item.Error.decodeInputs(
    data: ByteString,
    vararg path: String,
): Value = decodeInputs(name, inputs, data, *path)

public fun Abi.Item.Event.decodeData(
    data: ByteString,
    vararg path: String,
): Value = decodeData(Type.Tuple(null, inputs.filter { it.indexed.not() }), data, *path)

private fun decodeOutputs(
    outputs: Type.Tuple,
    data: ByteString,
    vararg path: String,
): Value = decodeData(outputs, data, *path)

private fun decodeInputs(
    name: String,
    inputs: Type.Tuple,
    data: ByteString,
    vararg path: String,
): Value {
    require(data.startsWith(encodeSignature(name, inputs).encodeUtf8().keccak256().selector()!!))
    return decodeData(inputs, data.substring(beginIndex = 4), *path)
}

private fun decodeData(
    type: Type.Tuple,
    data: ByteString,
    vararg path: String,
): Value {
    val buffer = Buffer().apply { write(data) }
    val decoder = if (path.isEmpty()) {
        AbiDecoder(source = buffer)
    } else {
        AbiPathDecoder(source = buffer, path = path.toList())
    }
    type.code(decoder)
    return decoder.value()
}

public fun Abi.Item.Event.decodeEvent(
    topics: List<ByteString>,
    data: ByteString,
): Value.Tuple {
    val topicValues = (decodeEventTopics(topics) as Value.Tuple).let(::ArrayDeque)
    val parameterValues = (decodeData(data) as Value.Tuple).let(::ArrayDeque)

    val values = inputs.map {
        if (it.indexed) {
            topicValues.removeFirst()
        } else {
            parameterValues.removeFirst()
        }
    }

    return Value.Tuple(values)
}

public fun Abi.Item.Event.decodeEvent(
    topics: List<ByteString>,
    data: ByteString,
    vararg path: String,
): Value {
    if (path.isEmpty()) {
        return decodeEvent(topics, data)
    }

    val topicsWithoutSignature = getTopicsWithoutSignature(topics)

    val name = path.first()
    val index = name.toIntOrNull()

    var indexedPosition = 0
    for (i in inputs.indices) {
        val parameter = inputs[i]
        if (parameter.indexed) {
            indexedPosition++
        }

        if (parameter.name == name || index == i) {
            if (parameter.indexed) {
                val topic = topicsWithoutSignature[indexedPosition]
                val buffer = Buffer().apply { write(topic) }
                val decoder = AbiDecoder(buffer)
                parameter.type.code(decoder)
                return decoder.value<Value>()
            } else {
                return decodeData(data, *path)
            }
        }
    }

    error("Not found in path ${path.joinToString()}")
}

public fun Abi.Item.Event.decodeEventTopics(
    topics: List<ByteString>,
): Value {
    val parameters = inputs.filter { it.indexed }

    val topicsWithoutSignature = getTopicsWithoutSignature(topics)

    val decodedValues = buildList(parameters.size) {
        for (index in parameters.indices) {
            val parameter = parameters[index]
            val topic = topicsWithoutSignature[index]

            val buffer = Buffer().apply { write(topic) }
            if (parameter.type is Type.FixedArray || parameter.type is Type.Tuple || parameter.type.isDynamic) {
                add(Value.Bytes(topic))
            } else {
                val decoder = AbiDecoder(buffer)
                parameter.type.code(decoder)
                add(decoder.value())
            }
        }
    }

    return Value.Tuple(decodedValues)
}

private fun Abi.Item.Event.getTopicsWithoutSignature(topics: List<ByteString>) =
    if (anonymous.not()) {
        require(topics[0] == encodeSignature().encodeUtf8().keccak256())
        require(topics.size - 1 == inputs.size)
        topics.drop(1)
    } else {
        require(topics.size == inputs.size)
        topics
    }
