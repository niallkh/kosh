package kosh.eth.wallet.transaction

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString
import kotlinx.io.write

internal data class SignatureData(
    val r: ByteString = ByteString(),
    val s: ByteString = ByteString(),
    val v: ByteString = ByteString(),
)

internal fun SignatureData.vbg(): BigInteger =
    BigInteger.fromByteArray(v.toByteArray(), Sign.POSITIVE)

internal fun SignatureData.concat(): ByteString = Buffer().apply {
    write(r)
    write(s)
    write(v)
}.readByteString()
