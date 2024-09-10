package kosh.domain.utils

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.BigInteger.Companion.ZERO

fun BigInteger?.orZero(): BigInteger = this ?: ZERO

fun UInt?.orZero(): UInt = this ?: 0u
fun ULong?.orZero(): ULong = this ?: 0u
fun Int?.orZero(): Int = this ?: 0
fun Long?.orZero(): Long = this ?: 0
