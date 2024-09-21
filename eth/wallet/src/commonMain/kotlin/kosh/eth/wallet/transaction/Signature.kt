package kosh.eth.wallet.transaction

import kotlinx.io.bytestring.ByteString

public data class Signature(
    val data: ByteString,
    val messageHash: ByteString,
)
