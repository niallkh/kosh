package kosh.libs.ledger.cmds

import kosh.libs.ledger.LedgerManager
import kosh.libs.ledger.StatusWord
import kosh.libs.ledger.exchange
import kosh.libs.ledger.ledgerAPDU
import okio.ByteString

suspend fun LedgerManager.Connection.getAppAndVersion(): AppData {

    val ledgerAPDU = ledgerAPDU(0xb0, 0x01, 0x00, 0x00)

    return exchange(ledgerAPDU) { sw ->
        sw.expectToBe(StatusWord.OK)

        require(readByte() == 0x01.toByte()) { "App and version format not supported" }
        val name = readByteString(readByte().toUByte().toLong()).utf8()
        val version = readByteString(readByte().toUByte().toLong()).utf8()
        val flags = if (!exhausted()) readByteString(readByte().toUByte().toLong())
        else ByteString.EMPTY
        AppData(name, version, flags)
    }
}

data class AppData(
    val name: String,
    val version: String,
    val flags: ByteString,
)
