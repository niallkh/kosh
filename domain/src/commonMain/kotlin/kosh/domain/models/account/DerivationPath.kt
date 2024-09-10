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
value class DerivationPath private constructor(val path: List<UInt>) {

    override fun toString(): String = buildString {
        append("m")
        path.forEach {
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
    inline get() = path[4]

val DerivationPath.ledgerAddressIndex: UInt
    inline get() = path[2]

val DerivationPath.slip44: UInt
    inline get() = path[1]

internal fun derivationPathOf(path: String): DerivationPath {
    val segments = path.split("/")
    check(segments.firstOrNull() == "m")

    return segments.asSequence()
        .drop(1)
        .map {
            if (it.last() == '\'') {
                it.dropLast(1).toUInt().hardened
            } else {
                it.toUInt()
            }
        }
        .toList()
        .let { DerivationPath(*it.toUIntArray()) }
}

private val UInt.hardened: UInt
    inline get() = hardenedIndex + this

private val UInt.unhardened: UInt
    inline get() = this - hardenedIndex

private val UInt.isHardened: Boolean
    inline get() = this >= hardenedIndex
