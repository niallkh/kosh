package kosh.eth.wallet.transaction

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kosh.eth.abi.rlp.DefaultRlpEncoder
import kosh.eth.abi.rlp.RlpType
import kosh.eth.abi.rlp.rlpListOfNotNull
import kosh.eth.abi.rlp.toRlp
import okio.Buffer
import okio.ByteString

internal fun Transaction.Legacy.encode(
    signature: SignatureData?,
): ByteString {
    val rlpList = rlpListOfNotNull(
        BigInteger.fromULong(nonce).toRlp,
        gasPrice.toRlp,
        BigInteger.fromULong(gasLimit).toRlp,
        to?.toRlp ?: RlpType.RlpString.EMPTY,
        value.toRlp,
        data.toRlp,
        signature?.v?.toRlp,
        signature?.r?.toRlp,
        signature?.s?.toRlp,
    )

    val buffer = Buffer()
    val encoder = DefaultRlpEncoder(buffer)
    rlpList.encode(encoder)
    return buffer.readByteString()
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
        to?.toRlp ?: RlpType.RlpString.EMPTY,
        value.toRlp,
        data.toRlp,
        RlpType.RlpList.EMPTY,
        signatureData?.vbg()?.let { it - 27.toBigInteger() }?.toRlp,
        signatureData?.r?.toRlp,
        signatureData?.s?.toRlp,
    )

    val buffer = Buffer()
    buffer.writeByte(checkNotNull(type))
    rlpList.encode(DefaultRlpEncoder(buffer))
    return buffer.readByteString()
}
