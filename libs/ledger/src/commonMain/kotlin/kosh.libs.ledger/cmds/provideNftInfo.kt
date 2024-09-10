package kosh.libs.ledger.cmds

import kosh.libs.ledger.LedgerManager
import kosh.libs.ledger.StatusWord
import kosh.libs.ledger.exchange
import kosh.libs.ledger.ledgerAPDU
import okio.ByteString

suspend fun LedgerManager.Connection.provideNft(
    nft: NftInfo,
): Int = exchange(ledgerAPDU(0xe0, 0x14, 0x00, 0x00) {
    write(nft.data)
}) { sw ->
    sw.expectToBe(StatusWord.OK)
    readByte().toUByte().toInt()
}

data class NftInfo(
    val data: ByteString,
)
