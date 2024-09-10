package kosh.eth.abi

import kosh.eth.abi.coder.encodeSignature
import kosh.eth.abi.json.JsonAbi
import kosh.eth.abi.json.toAbi
import kotlinx.serialization.json.JsonElement
import okio.ByteString
import okio.ByteString.Companion.encodeUtf8

public data class Abi(
    val items: List<Item>,
) : List<Abi.Item> by items {

    public sealed class Item {

        public data class Constructor(
            val inputs: Type.Tuple,
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
            val inputs: Type.Tuple,
            val outputs: Type.Tuple,
            val stateMutability: StateMutability,
        ) : Item() {
            public val selector: ByteString by lazy {
                encodeSignature().encodeUtf8().keccak256().selector()!!
            }
        }

        public data class Error(
            val name: String,
            val inputs: Type.Tuple,
        ) : Item() {
            public val selector: ByteString by lazy {
                encodeSignature().encodeUtf8().keccak256().selector()!!
            }
        }

        public data class Event(
            val anonymous: Boolean,
            val name: String,
            val inputs: Type.Tuple,
        ) : Item() {
            public val selector: ByteString by lazy {
                encodeSignature().encodeUtf8().keccak256().selector()!!
            }
        }

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

public fun ByteString.selector(): ByteString? {
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
        is Abi.Item.Function -> selector
        is Abi.Item.Event -> selector
        is Abi.Item.Error -> selector
        is Abi.Item.Constructor -> null
        is Abi.Item.Fallback -> null
        is Abi.Item.Receive -> null
    }


