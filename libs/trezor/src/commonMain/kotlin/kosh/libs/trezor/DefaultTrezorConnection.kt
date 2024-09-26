package kosh.libs.trezor

import arrow.core.raise.catch
import co.touchlab.kermit.Logger
import com.satoshilabs.trezor.lib.protobuf.ButtonAck
import com.satoshilabs.trezor.lib.protobuf.ButtonRequest
import com.satoshilabs.trezor.lib.protobuf.Cancel
import com.satoshilabs.trezor.lib.protobuf.Failure
import com.satoshilabs.trezor.lib.protobuf.Features
import com.satoshilabs.trezor.lib.protobuf.Initialize
import com.satoshilabs.trezor.lib.protobuf.MessageType
import com.satoshilabs.trezor.lib.protobuf.PassphraseAck
import com.satoshilabs.trezor.lib.protobuf.PassphraseRequest
import com.satoshilabs.trezor.lib.protobuf.PinMatrixAck
import com.satoshilabs.trezor.lib.protobuf.PinMatrixRequest
import com.squareup.wire.Message
import kosh.libs.transport.Transport
import kosh.libs.trezor.TrezorManager.Connection.Listener.TrezorFailureException
import kosh.libs.trezor.cmds.expect
import kosh.libs.trezor.cmds.expectOrNull
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteArray
import kotlinx.io.readByteString
import kotlinx.io.write

internal class DefaultTrezorConnection(
    private val connection: Transport.Connection,
    private val listener: TrezorManager.Connection.Listener,
    private val sessionCache: SessionCache,
) : TrezorManager.Connection {

    private val logger = Logger.withTag("[K]TrezorManager.Connection")

    private var features: Features? = null

    override suspend fun init(
        newSession: Boolean,
    ): Features {
        logger.v { "init(newSession = $newSession)" }
        val sessionId = if (!newSession) sessionCache.get() else null
        logger.v { "saved sessionId = ${sessionId?.value?.hex()}" }

        cancel()
        val response = exchange(Initialize(session_id = sessionId?.value))

        val features = response.expect<Features>()
        saveFeatures(features)
        return features
    }

    override suspend fun exchange(
        message: Message<*, *>,
    ): Message<*, *> = when (val response = exchangeRaw(message)) {
        is PinMatrixRequest -> pinMatrixRequest()
        is PassphraseRequest -> passphraseRequest()
        is ButtonRequest -> buttonRequest(response)
        is Failure -> throw TrezorFailureException(response.code, response.message)
        else -> response
    }

    private suspend fun exchangeRaw(
        message: Message<*, *>,
    ): Message<*, *> {
        logger.d { "-> ${message::class.simpleName}" }
        write(message)
        logger.v { "---" }
        val response = read()
        logger.d { "<- ${response::class.simpleName}" }
        return response
    }

    private suspend fun write(message: Message<*, *>) {
        connection.write(encode(message))
    }

    private suspend fun read(): Message<*, *> {
        val buffer = Buffer().apply { write(connection.read()) }

        val msg = buffer.readByteString((buffer.size - 2).toInt())
        val msgType = buffer.readShort()

        return decode(msgType = msgType, data = Buffer().apply { write(msg) })
    }

    private suspend fun cancel() {
        logger.v { "cancel()" }
        var response = exchangeRaw(Cancel())

        while (true) {
            if (response.expectOrNull<Failure>()?.code == Failure.FailureType.Failure_ActionCancelled) {
                break
            }
            response = read()
        }
    }

    private suspend fun pinMatrixRequest(): Message<*, *> {
        logger.v { "listener#pinMatrixRequest" }
        val pin = catch({
            listener.pinMatrixRequest()
        }) {
            withContext(NonCancellable) {
                this@DefaultTrezorConnection.cancel()
            }
            throw it
        }

        return exchange(PinMatrixAck(pin = pin))
    }

    private suspend fun passphraseRequest(): Message<*, *> {
        logger.v { "listener#passphraseRequest" }
        val passphrase = catch({
            listener.passphraseRequest()
        }) {
            withContext(NonCancellable) {
                this@DefaultTrezorConnection.cancel()
            }
            throw it
        }

        return exchange(
            PassphraseAck(passphrase = passphrase, on_device = passphrase == null)
        )
    }

    private suspend fun buttonRequest(
        response: ButtonRequest,
    ): Message<*, *> {
        logger.v { "listener#onButtonRequest(type = ${response.code})" }
        catch({
            listener.onButtonRequest(response.code)
        }) {
            withContext(NonCancellable) {
                this@DefaultTrezorConnection.cancel()
            }
        }

        return exchange(ButtonAck())
    }

    private fun saveFeatures(
        features: Features,
    ) {
        if (features.session_id != null) {
            sessionCache.set(SessionId(features.session_id))
            logger.v { "updated sessionId = ${features.session_id.hex()}" }
        }

        this.features = features.copy(session_id = null)
    }

    private fun encode(message: Message<*, *>): ByteString = Buffer().run {
        write(message.encode())
        writeShort(message.toMessageType().value.toShort())
        readByteString()
    }

    private fun decode(
        msgType: Short,
        data: Source,
    ): Message<*, *> {
        val messageType = checkNotNull(MessageType.fromValue(msgType.toInt())) {
            "Unknown message type $msgType"
        }

        return messageType.toAdapter().decode(data.readByteArray())
    }
}
