package kosh.domain.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ChainAddress(val chainId: ChainId, val address: Address) {
    companion object {
        fun fromCaip10(caip10: String): ChainAddress {
            val (namespace, chainId, address) = caip10.split(":")
            require(namespace == "eip155")
            return ChainAddress(
                chainId = ChainId(chainId.toULong()),
                address = Address(address).getOrNull()!!
            )
        }
    }
}

infix fun ChainId.at(address: Address) = ChainAddress(this, address)

val ChainAddress.did: String
    get() = "did:pkh:${chainId.caip2()}:${address.eip55()}"

fun ChainAddress.caip10() = "eip155:${chainId.value}:${address.eip55()}"
