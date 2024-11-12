package kosh.libs.keystone

import kotlinx.io.bytestring.ByteString

data class KeystoneEAPDU(
    val cla: Byte = 0,
    val ins: Short,
    val p1: Short,
    val p2: Short,
    val lc: Short, // request id
    val data: ByteString,
)
