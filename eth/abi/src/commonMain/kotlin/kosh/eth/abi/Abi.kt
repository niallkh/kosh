package kosh.eth.abi

import kosh.eth.abi.coder.AbiType
import kosh.eth.abi.coder.encodeSignature
import kosh.eth.abi.json.JsonAbi
import kosh.eth.abi.json.toAbi
import kotlinx.io.bytestring.ByteString
import kotlinx.serialization.json.JsonElement

public data class Abi(
    val items: List<Item>,
) : List<Abi.Item> by items {

    public sealed class Item {

        public data class Constructor(
            val inputs: AbiType.Tuple,
            val stateMutability: StateMutability = StateMutability.NonPayable,
        ) : Item()

        public data class Fallback(
            val stateMutability: StateMutability = StateMutability.NonPayable,
        ) : Item()

        public data class Receive(
            val stateMutability: StateMutability,
        ) : Item()

        public data class Function(
            val name: String,
            val inputs: AbiType.Tuple,
            val outputs: AbiType.Tuple,
            val stateMutability: StateMutability,
        ) : Item()

        public data class Error(
            val name: String,
            val inputs: AbiType.Tuple,
        ) : Item()

        public data class Event(
            val anonymous: Boolean,
            val name: String,
            val inputs: AbiType.Tuple,
        ) : Item()

        public enum class StateMutability {
            Pure,
            View,
            NonPayable,
            Payable,
        }
    }

    public companion object {
        public fun from(json: String): Abi = JsonAbi.from(json).toAbi()
        public fun from(element: JsonElement): Abi = JsonAbi.from(element).toAbi()
    }
}

public fun ByteString.functionSelector(): ByteString? {
    if (size < 4) return null
    return substring(0, 4)
}

public val Abi.Item.name: String?
    get() = when (this) {
        is Abi.Item.Function -> name
        is Abi.Item.Event -> name
        is Abi.Item.Error -> name
        is Abi.Item.Constructor -> null
        is Abi.Item.Fallback -> null
        is Abi.Item.Receive -> null
    }

public val Abi.Item.selector: ByteString?
    get() = when (this) {
        is Abi.Item.Function -> encodeSignature().keccak256().functionSelector()
        is Abi.Item.Event -> encodeSignature().keccak256()
        is Abi.Item.Error -> encodeSignature().keccak256().functionSelector()
        is Abi.Item.Constructor -> null
        is Abi.Item.Fallback -> null
        is Abi.Item.Receive -> null
    }
