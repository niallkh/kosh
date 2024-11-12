package kosh.domain.models.account

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

private const val hardenedIndex: UInt = 0x80000000u

@JvmInline
@Serializable
@Immutable
value class DerivationPath private constructor(val components: List<UInt>) {

    override fun toString(): String = buildString {
        append("m")
        components.forEach {
            append("/")
            if (it.isHardened) {
                append(it.unhardened)
                append("'")
            } else {
                append(it)
            }
        }
    }

    companion object {
        private val EMPTY = DerivationPath(persistentListOf())

        operator fun invoke(vararg path: UInt): DerivationPath =
            if (path.isEmpty()) EMPTY else DerivationPath(path.toImmutableList())
    }
}

fun ethereumDerivationPath(index: UInt = 0u): DerivationPath {
    return DerivationPath(44u.hardened, 60u.hardened, 0u.hardened, 0u, index)
}

fun ledgerDerivationPath(index: UInt = 0u): DerivationPath {
    return DerivationPath(44u.hardened, 60u.hardened, index.hardened, 0u, 0u)
}

val DerivationPath.ethereumAddressIndex: UInt
    get() = components[4]

val DerivationPath.ledgerAddressIndex: UInt
    get() = components[2].unhardened

val DerivationPath.slip44: UInt
    get() = components[1]

private val UInt.hardened: UInt
    inline get() = hardenedIndex + this

private val UInt.unhardened: UInt
    inline get() = this - hardenedIndex

private val UInt.isHardened: Boolean
    inline get() = this >= hardenedIndex
