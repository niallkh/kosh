package kosh.eth.wallet.transaction

import com.ionspin.kotlin.bignum.integer.BigInteger
import okio.ByteString

public sealed interface Transaction {

    public val chainId: ULong

    public data class Legacy(
        override val chainId: ULong,
        val nonce: ULong,
        val gasPrice: BigInteger,
        val gasLimit: ULong,
        val to: ByteString?,
        val value: BigInteger,
        val data: ByteString,
    ) : Transaction

    public data class Type1559(
        override val chainId: ULong,
        val nonce: ULong,
        val maxPriorityFeePerGas: BigInteger,
        val maxFeePerGas: BigInteger,
        val gasLimit: ULong,
        val to: ByteString?,
        val value: BigInteger,
        val data: ByteString,
    ) : Transaction
}

internal val Transaction.type: Int?
    get() = when (this) {
        is Transaction.Legacy -> null
        is Transaction.Type1559 -> 0x02
    }
