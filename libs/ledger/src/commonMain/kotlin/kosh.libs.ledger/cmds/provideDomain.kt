package kosh.libs.ledger.cmds

import kosh.libs.ledger.LedgerManager
import kosh.libs.ledger.exchange
import kosh.libs.ledger.expectSuccess
import kosh.libs.ledger.ledgerAPDU
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.write

suspend fun LedgerManager.Connection.provideDomain(
    domain: DomainInfo,
) {
    val messageBuffer = Buffer().apply { write(domain.data) }

    exchange(ledgerAPDU(0xe0, 0x22, 0x01, 0x00) {
        writeInt(domain.data.size)
        write(messageBuffer.readChunk(253))
    }) { sw ->
        sw.expectSuccess()
    }

    while (!messageBuffer.exhausted()) {
        exchange(ledgerAPDU(0xe0, 0x22, 0x00, 0x00) {
            write(messageBuffer.readChunk(255))
        }) { sw ->
            sw.expectSuccess()
        }
    }
}

data class DomainInfo(
    val data: ByteString,
)
