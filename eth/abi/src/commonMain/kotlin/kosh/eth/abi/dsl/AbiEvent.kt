package kosh.eth.abi.dsl

import kosh.eth.abi.Abi
import kosh.eth.abi.Type
import kotlin.properties.ReadOnlyProperty


public inline fun abiEvent(
    anonymous: Boolean = false,
    crossinline inputs: AbiTupleDsl.() -> Unit = {},
): ReadOnlyProperty<Any?, Abi.Item.Event> {
    lateinit var name: String

    val item = lazy {
        Abi.Item.Event(
            name = name,
            inputs = Type.Tuple(null, AbiTupleDsl().apply(inputs).parameters),
            anonymous = anonymous
        )
    }

    return ReadOnlyProperty { _, prop ->
        if (item.isInitialized().not()) {
            name = prop.name
            item.value.also {
                name = ""
            }
        } else {
            item.value
        }
    }
}
