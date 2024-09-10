package kosh.eth.abi

import kosh.eth.abi.json.JsonEip712
import kosh.eth.abi.json.toEip712
import kosh.eth.abi.json.toEip712V2
import kotlinx.serialization.json.Json

public data class Eip712(
    val domain: Message,
    val message: Message,
) {

    public data class Message(
        val type: Type.Tuple,
        val value: Value.Tuple,
    )

    public companion object {
        public fun fromJson(
            text: String,
            json: Json = Json,
        ): Eip712 = json.decodeFromString<JsonEip712>(text).toEip712()
    }
}

public data class Eip712V2(
    val types: Map<String, Type.Tuple>,
    val domain: Domain,
    val primaryType: String,
    val message: Value.Tuple,
) {

    public data class Domain(
        val name: Value.String? = null,
        val version: Value.String? = null,
        val chainId: Value.BigNumber? = null,
        val verifyingContract: Value.Address? = null,
        val salt: Value.Bytes? = null,
    )

    public companion object {
        public fun fromJson(
            text: String,
            json: Json = Json,
        ): Eip712V2 = json.decodeFromString<JsonEip712>(text).toEip712V2()
    }
}

public fun Eip712V2.Domain.toTuple(): Value.Tuple = Value.Tuple(
    listOfNotNull(name, version, chainId, verifyingContract, salt)
)
