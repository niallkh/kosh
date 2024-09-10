package kosh.libs.ledger.cmds

import kosh.libs.ledger.LedgerManager
import kosh.libs.ledger.StatusWord
import kosh.libs.ledger.exchange
import kosh.libs.ledger.ledgerAPDU
import kosh.libs.ledger.toInt

suspend fun LedgerManager.Connection.ethereumAddress(
    derivationPath: List<UInt>,
    showDisplay: Boolean = false,
    chainCode: Boolean = false,
): String {
    val ledgerAPDU = ledgerAPDU(
        cla = 0xe0,
        ins = 0x02,
        p1 = showDisplay.toInt(),
        p2 = chainCode.toInt(),
    ) {
        writeByte(derivationPath.size)
        derivationPath.forEach {
            writeInt(it.toInt())
        }
    }

    return exchange(ledgerAPDU) { sw ->
        sw.expectToBe(StatusWord.OK)

        readByteString(readByte().toUByte().toLong())
        "0x" + readByteString(readByte().toUByte().toLong()).utf8()
    }
}
