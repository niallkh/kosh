package kosh.libs.ledger.cmds

import kosh.libs.ledger.LedgerManager
import kosh.libs.ledger.exchange
import kosh.libs.ledger.expectSuccess
import kosh.libs.ledger.ledgerAPDU
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString
import kotlinx.io.write

suspend fun LedgerManager.Connection.signTransaction(
    derivationPath: List<UInt>,
    transaction: ByteString,
    parameters: SignTransactionParameters?,
): ByteString {
    val messageBuffer = Buffer().apply { write(transaction) }

    parameters?.domains?.forEach {
        provideDomain(it)
    }

    parameters?.plugins?.forEach {
        setPlugin(it)
    }

    parameters?.externalPlugins?.forEach {
        setExternalPlugin(it)
    }

    parameters?.tokens?.forEach {
        provideToken(it)
    }

    parameters?.nfts?.forEach {
        provideNft(it)
    }

    var response = exchange(ledgerAPDU(0xe0, 0x04, 0x00, 0x00) {
        writeByte(derivationPath.size.toByte())
        derivationPath.forEach {
            writeInt(it.toInt())
        }

        write(messageBuffer.readChunk(230))
    }) { sw ->
        sw.expectSuccess()
        readByteString()
    }

    while (!messageBuffer.exhausted()) {
        val chunk = messageBuffer.readChunk(255)

        val ledgerAPDU = ledgerAPDU(0xe0, 0x04, 0x80, 0x00) {
            write(chunk)
        }

        response = exchange(ledgerAPDU) { sw ->
            sw.expectSuccess()
            readByteString()
        }
    }

    return Buffer().run {
        write(response, 1, 65)
        writeByte((response[0] + 27).toByte())
        readByteString()
    }
}
