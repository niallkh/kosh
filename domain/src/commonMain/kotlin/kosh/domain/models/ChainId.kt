package kosh.domain.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline


@JvmInline
@Serializable
@Immutable
value class ChainId private constructor(val value: ULong) {

    override fun toString(): String = value.toString()

    companion object {
        private val memo = ::ChainId

        operator fun invoke(id: ULong) = memo(id)

        fun fromCaip2(caip2: String): ChainId {
            val (namespace, chain) = caip2.split(":")
            require(namespace == "eip155")
            return memo(chain.toULong())
        }
    }
}

fun ChainId.caip2() = "eip155:$this"

val zeroChain: ChainId = ChainId(0u)
val ethereum: ChainId = ChainId(1u)
val optimism: ChainId = ChainId(10u)
val bnbChain: ChainId = ChainId(56u)
val gnosis: ChainId = ChainId(100u)
val polygonPos: ChainId = ChainId(137u)
val base: ChainId = ChainId(8453u)
val arbitrumOne: ChainId = ChainId(42161u)
val avalanche: ChainId = ChainId(43114u)
val celo: ChainId = ChainId(42220u)
val sepolia: ChainId = ChainId(11155111u)

fun ChainId?.orZero() = this ?: zeroChain
