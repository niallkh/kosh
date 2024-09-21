package kosh.libs.ledger.cmds

import co.touchlab.kermit.Logger
import kosh.libs.ledger.LedgerManager
import kosh.libs.ledger.StatusWord
import kosh.libs.ledger.exchange
import kosh.libs.ledger.ledgerAPDU
import kotlinx.io.bytestring.ByteString
import kotlinx.io.write

suspend fun LedgerManager.Connection.setPlugin(
    token: PluginInfo,
): Unit = exchange(ledgerAPDU(0xe0, 0x16, 0x00, 0x00) {
    write(token.data)
}) { sw ->
    when (sw) {
        StatusWord.OK -> Unit
        StatusWord.PLUGIN_NOT_PRESENT -> Logger.d { "Plugin not present: ${token.data}" }
        else -> error("Unexpected Status Word: $sw")
    }
}

data class PluginInfo(
    val data: ByteString,
)
