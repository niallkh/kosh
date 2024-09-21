package kosh.eth.abi.dsl

import kosh.eth.abi.Abi
import kosh.eth.abi.coder.AbiType
import kotlin.properties.ReadOnlyProperty

public inline fun abiFunction(
    stateMutability: Abi.Item.StateMutability = Abi.Item.StateMutability.NonPayable,
    crossinline outputs: AbiTupleDsl.() -> Unit,
    crossinline inputs: AbiTupleDsl.() -> Unit = {},
): ReadOnlyProperty<Any?, Abi.Item.Function> {
    lateinit var name: String

    val item = lazy {
        Abi.Item.Function(
            name = name,
            inputs = AbiType.Tuple(null, AbiTupleDsl().apply(inputs).parameters),
            outputs = AbiType.Tuple(null, AbiTupleDsl().apply(outputs).parameters),
            stateMutability = stateMutability,
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


public inline fun abiFunction(
    stateMutability: Abi.Item.StateMutability = Abi.Item.StateMutability.NonPayable,
    crossinline inputs: AbiTupleDsl.() -> Unit,
): ReadOnlyProperty<Any?, Abi.Item.Function> = abiFunction(stateMutability, inputs, inputs)

public inline fun abiViewFunction(
    crossinline inputs: AbiTupleDsl.() -> Unit,
): ReadOnlyProperty<Any?, Abi.Item.Function> =
    abiFunction(Abi.Item.StateMutability.View, inputs, inputs)

public inline fun abiPayableFunction(
    crossinline inputs: AbiTupleDsl.() -> Unit,
): ReadOnlyProperty<Any?, Abi.Item.Function> =
    abiFunction(Abi.Item.StateMutability.Payable, inputs, inputs)

public inline fun abiViewFunction(
    crossinline outputs: AbiTupleDsl.() -> Unit,
    crossinline inputs: AbiTupleDsl.() -> Unit = {},
): ReadOnlyProperty<Any?, Abi.Item.Function> =
    abiFunction(Abi.Item.StateMutability.View, outputs, inputs)
