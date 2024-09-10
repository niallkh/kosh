package kosh.eth.abi.delegate

import kosh.eth.abi.Abi
import kosh.eth.abi.Value
import kosh.eth.abi.coder.decodeEvent
import kosh.eth.abi.coder.decodeInputs
import kosh.eth.abi.coder.decodeOutputs
import okio.ByteString
import kotlin.properties.ReadOnlyProperty

public inline fun <reified V : Value> Abi.Item.Function.decode(
    data: ByteString,
): ReadOnlyProperty<Any?, V> {
    lateinit var name: String
    val decoded = lazy { decodeOutputs(data, name) }
    return ReadOnlyProperty { _, prop ->
        if (decoded.isInitialized().not()) {
            name = prop.name
            decoded.value.also {
                name = ""
            } as V
        } else {
            decoded.value as V
        }
    }
}

public inline fun <reified V : Value> Abi.Item.Function.decodeInputs(
    data: ByteString,
): ReadOnlyProperty<Any?, V> {
    lateinit var name: String
    val decoded = lazy { decodeInputs(data, name) }
    return ReadOnlyProperty { _, prop ->
        if (decoded.isInitialized().not()) {
            name = prop.name
            decoded.value.also {
                name = ""
            } as V
        } else {
            decoded.value as V
        }
    }
}

public inline fun <reified V : Value> Abi.Item.Event.decode(
    topics: List<ByteString>,
    data: ByteString,
): ReadOnlyProperty<Any?, V> {
    lateinit var name: String
    val decoded = lazy { decodeEvent(topics, data, name) }
    return ReadOnlyProperty { _, prop ->
        if (decoded.isInitialized().not()) {
            name = prop.name
            decoded.value.also {
                name = ""
            } as V
        } else {
            decoded.value as V
        }
    }
}
