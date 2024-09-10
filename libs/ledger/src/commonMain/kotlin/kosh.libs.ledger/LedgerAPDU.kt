package kosh.libs.ledger

import okio.Buffer
import okio.BufferedSink
import okio.ByteString

data class LedgerAPDU(
    val cla: Int,
    val ins: Int,
    val p1: Int,
    val p2: Int,
    val data: ByteString,
)

fun ledgerAPDU(
    cla: Int,
    ins: Int,
    p1: Int,
    p2: Int,
    data: BufferedSink.() -> Unit = {},
) = LedgerAPDU(cla, ins, p1, p2, Buffer().apply { data() }.readByteString())

fun Boolean.toInt(): Int = if (this) 0x01 else 0x00

