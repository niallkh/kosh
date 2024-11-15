package kosh.libs.ledger.cmds

import kosh.eth.abi.Value
import kosh.libs.ledger.LedgerManager
import kosh.libs.ledger.exchange
import kosh.libs.ledger.expectSuccess
import kosh.libs.ledger.ledgerAPDU
import kotlinx.io.bytestring.ByteString
import kotlinx.io.write

suspend fun LedgerManager.Connection.provideToken(
    token: TokenInfo,
): Int = exchange(ledgerAPDU(0xe0, 0x0a, 0x00, 0x00) {
//    writeByte(token.ticker.length)
//    writeUtf8(token.ticker)
//    write(token.address.value)
//    writeInt(token.decimals.toInt())
//    writeInt(token.chainId.toInt())
//    write(token.signature)

    write(token.data)
}) { sw ->
    sw.expectSuccess()
    readByte().toUByte().toInt()
}

data class TokenInfo(
    val ticker: String,
    val address: Value.Address,
    val decimals: UInt,
    val chainId: UInt,
    val data: ByteString,
    val signature: ByteString,
)
