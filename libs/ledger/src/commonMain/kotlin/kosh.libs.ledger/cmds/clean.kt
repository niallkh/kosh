package kosh.libs.ledger.cmds

import kosh.libs.ledger.LedgerManager
import kosh.libs.ledger.exchange
import kosh.libs.ledger.expectSuccess
import kosh.libs.ledger.ledgerAPDU

suspend fun LedgerManager.Connection.clean() {
    val ledgerAPDU = ledgerAPDU(0xe0, 0x50, 0x0, 0x0)

    exchange(ledgerAPDU) { sw ->
        sw.expectSuccess()
    }
}
