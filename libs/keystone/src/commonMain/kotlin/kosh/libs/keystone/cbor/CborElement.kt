package kosh.libs.keystone.cbor

import kotlinx.io.bytestring.ByteString
import kotlin.jvm.JvmInline

sealed interface CborElement {

    @JvmInline
    value class CborMap(
        private val map: Map<CborElement, CborElement>,
    ) : CborElement, Map<CborElement, CborElement> by map {

        companion object {
            operator fun invoke(
                vararg pairs: Pair<CborElement, CborElement>?,
            ) = CborMap(pairs.filterNotNull().toMap())
        }
    }

    @JvmInline
    value class CborArray(
        private val array: List<CborElement>,
    ) : CborElement, List<CborElement> by array {

        companion object {
            operator fun invoke(
                vararg elements: CborElement?,
            ) = CborArray(elements.filterNotNull().toList())
        }
    }

    @JvmInline
    value class CborUInt(
        val value: ULong,
    ) : CborElement {

        companion object {
            operator fun invoke(value: Int) = CborUInt(value.toULong())
        }
    }

    @JvmInline
    value class CborByteString(
        val value: ByteString,
    ) : CborElement

    @JvmInline
    value class CborTextString(
        val value: String,
    ) : CborElement

    data object CborNull : CborElement

    data object CborUndefined : CborElement

    @JvmInline
    value class CborBoolean private constructor(
        val value: Boolean,
    ) : CborElement {

        companion object {
            private val CBOR_TRUE = CborBoolean(true)
            private val CBOR_FALSE = CborBoolean(false)

            operator fun invoke(value: Boolean) = if (value) CBOR_TRUE else CBOR_FALSE
        }
    }

    data class CborTagged(
        val tag: ULong,
        val value: CborElement,
    ) : CborElement
}

val CborElement.cborArray: CborElement.CborArray
    inline get() = this as? CborElement.CborArray ?: error("Expected CborArray")
val CborElement.cborMap: CborElement.CborMap
    inline get() = this as? CborElement.CborMap ?: error("Expected CborMap")
val CborElement.cborUInt: CborElement.CborUInt
    inline get() = this as? CborElement.CborUInt ?: error("Expected CborUInt")
val CborElement.cborBoolean: CborElement.CborBoolean
    inline get() = this as? CborElement.CborBoolean ?: error("Expected CborBoolean")
val CborElement.cborTagged: CborElement.CborTagged
    inline get() = this as? CborElement.CborTagged ?: error("Expected CborTagged")
val CborElement.cborNull: CborElement.CborNull
    inline get() = this as? CborElement.CborNull ?: error("Expected CborNull")
val CborElement.cborTextString: CborElement.CborTextString
    inline get() = this as? CborElement.CborTextString ?: error("Expected CborTextString")
val CborElement.cborByteString: CborElement.CborByteString
    inline get() = this as? CborElement.CborByteString ?: error("Expected CborByteString")
val CborElement.cborUndefined: CborElement.CborUndefined
    inline get() = this as? CborElement.CborUndefined ?: error("Expected CborUndefined")
val UInt.cborUInt: CborElement.CborUInt
    inline get() = CborElement.CborUInt(toULong())
val ULong.cborUInt: CborElement.CborUInt
    inline get() = CborElement.CborUInt(this)
val String.cborTextString: CborElement.CborTextString
    inline get() = CborElement.CborTextString(this)
val Boolean.cborBoolean: CborElement.CborBoolean
    inline get() = CborElement.CborBoolean(this)
val ByteString.cborByteString: CborElement.CborByteString
    inline get() = CborElement.CborByteString(this)
val String.cborByteString: CborElement.CborTextString
    inline get() = CborElement.CborTextString(this)
val List<CborElement>.cborArray: CborElement.CborArray
    inline get() = CborElement.CborArray(this)
