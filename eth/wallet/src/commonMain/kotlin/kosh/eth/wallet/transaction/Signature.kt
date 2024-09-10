package kosh.eth.wallet.transaction

import okio.ByteString

public data class Signature(
    val data: ByteString,
    val messageHash: ByteString,
)
