package kosh.eth.wallet.transaction

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kosh.eth.abi.plus
import kosh.eth.abi.rlp.Rlp
import kosh.eth.abi.rlp.encode
import kosh.eth.abi.rlp.rlpListOfNotNull
import kosh.eth.abi.rlp.toRlp
import kotlinx.io.bytestring.ByteString

internal fun Transaction.Legacy.encode(
    signature: SignatureData?,
): ByteString {
    val rlpList = rlpListOfNotNull(
        BigInteger.fromULong(nonce).toRlp,
        gasPrice.toRlp,
        BigInteger.fromULong(gasLimit).toRlp,
        to?.toRlp ?: Rlp.RlpString.EMPTY,
        value.toRlp,
        data.toRlp,
        signature?.v?.toRlp,
        signature?.r?.toRlp,
        signature?.s?.toRlp,
    )

    return rlpList.encode()
}

public fun Transaction.Type1559.encode(): ByteString = encode(signatureData = null)

internal fun Transaction.Type1559.encode(
    signatureData: SignatureData?,
): ByteString {
    val rlpList = rlpListOfNotNull(
        BigInteger.fromULong(chainId).toRlp,
        BigInteger.fromULong(nonce).toRlp,
        maxPriorityFeePerGas.toRlp,
        maxFeePerGas.toRlp,
        BigInteger.fromULong(gasLimit).toRlp,
        to?.toRlp ?: Rlp.RlpString.EMPTY,
        value.toRlp,
        data.toRlp,
        Rlp.RlpList.EMPTY,
        signatureData?.vbg()?.let { it - 27.toBigInteger() }?.toRlp,
        signatureData?.r?.toRlp,
        signatureData?.s?.toRlp,
    )

    return ByteString(checkNotNull(type).toByte()) + rlpList.encode()
}
