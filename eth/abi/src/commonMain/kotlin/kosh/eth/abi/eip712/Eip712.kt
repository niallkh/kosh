package kosh.eth.abi.eip712

import kosh.eth.abi.Value
import kosh.eth.abi.eip712.Eip712Type.Tuple.Parameter
import kosh.eth.abi.json.JsonEip712
import kosh.eth.abi.json.toEip712
import kotlinx.serialization.json.Json

internal typealias Eip712Types = Map<Eip712Type.Tuple, List<Parameter>>

public data class Eip712(
    val types: Eip712Types,
    val domain: Domain,
    val primaryType: Eip712Type.Tuple,
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
        ): Eip712 = json.decodeFromString<JsonEip712>(text).toEip712()
    }
}

public fun Eip712.Domain.toValue(): Value.Tuple = Value.Tuple(
    mapOf(
        "name" to name,
        "version" to version,
        "chainId" to chainId,
        "verifyingContract" to verifyingContract,
        "salt" to salt,
    )
        .filterValues { it != null }
        .mapValues { (_, v) -> v!! }
)

public fun Eip712Types.domain(): List<Parameter> = getValue(Eip712Type.Tuple.Domain)
