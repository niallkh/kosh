package kosh.libs.ledger.cmds

import co.touchlab.kermit.Logger
import kosh.libs.ledger.LedgerManager
import kosh.libs.ledger.StatusWord
import kosh.libs.ledger.exchange
import kosh.libs.ledger.ledgerAPDU
import okio.ByteString

suspend fun LedgerManager.Connection.setExternalPlugin(
    token: ExternalPluginInfo,
): Unit = exchange(ledgerAPDU(0xe0, 0x12, 0x00, 0x00) {
    write(token.data)
    write(token.signature)
}) { sw ->
    when (sw) {
        StatusWord.OK -> Unit
        StatusWord.PLUGIN_NOT_PRESENT -> Logger.d { "Plugin not present: ${token.data}" }
        else -> error("Unexpected Status Word: $sw")
    }
}

data class ExternalPluginInfo(
    val name: String,
    val data: ByteString,
    val signature: ByteString,
)
