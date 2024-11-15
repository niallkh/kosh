package kosh.libs.ledger

import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString

data class LedgerAPDU(
    val cla: Byte,
    val ins: Byte,
    val p1: Byte,
    val p2: Byte,
    val data: ByteString,
)

fun ledgerAPDU(
    cla: Int,
    ins: Int,
    p1: Int,
    p2: Int,
    data: Sink.() -> Unit = {},
) = LedgerAPDU(
    cla.toByte(),
    ins.toByte(),
    p1.toByte(),
    p2.toByte(),
    Buffer().apply { data() }.readByteString()
)

fun Boolean.toInt(): Int = if (this) 1 else 0

