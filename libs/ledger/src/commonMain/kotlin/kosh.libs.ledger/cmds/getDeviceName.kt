package kosh.libs.ledger.cmds

import kosh.libs.ledger.LedgerManager
import kosh.libs.ledger.StatusWord
import kosh.libs.ledger.exchange
import kosh.libs.ledger.ledgerAPDU
import kotlinx.io.bytestring.decodeToString
import kotlinx.io.readByteString

suspend fun LedgerManager.Connection.getDeviceName(): String {

    clean()

    val ledgerAPDU = ledgerAPDU(0xe0, 0xd2, 0x00, 0x00)

    return exchange(ledgerAPDU) { sw ->
        sw.expectToBe(StatusWord.OK)

        readByteString().decodeToString()
    }
}
