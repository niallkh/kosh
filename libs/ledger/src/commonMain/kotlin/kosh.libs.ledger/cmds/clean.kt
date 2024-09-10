package kosh.libs.ledger.cmds

import kosh.libs.ledger.LedgerManager
import kosh.libs.ledger.StatusWord
import kosh.libs.ledger.exchange
import kosh.libs.ledger.ledgerAPDU

suspend fun LedgerManager.Connection.clean() {
    val ledgerAPDU = ledgerAPDU(0xe0, 0x50, 0x00, 0x00)

    exchange(ledgerAPDU) { sw ->
        sw.expectToBe(StatusWord.OK)
    }
}
