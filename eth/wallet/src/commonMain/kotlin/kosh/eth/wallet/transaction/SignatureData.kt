package kosh.eth.wallet.transaction

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import okio.Buffer
import okio.ByteString

internal data class SignatureData(
    val r: ByteString = ByteString.EMPTY,
    val s: ByteString = ByteString.EMPTY,
    val v: ByteString = ByteString.EMPTY,
)

internal fun SignatureData.vbg(): BigInteger =
    BigInteger.fromByteArray(v.toByteArray(), Sign.POSITIVE)

internal fun SignatureData.concat(): ByteString = Buffer().apply {
    write(r)
    write(s)
    write(v)
}.readByteString()
