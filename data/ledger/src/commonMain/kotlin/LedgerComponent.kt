package kosh.data.trezor

import kosh.libs.ledger.LedgerManager

interface LedgerComponent {
    val ledgerManager: LedgerManager
}
