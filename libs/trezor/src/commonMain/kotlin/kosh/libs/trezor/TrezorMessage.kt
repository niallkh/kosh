package kosh.libs.trezor

import com.satoshilabs.trezor.lib.protobuf.MessageType
import com.squareup.wire.Message
import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.Source
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import kotlinx.io.readByteArray
import kotlinx.io.readByteString
import kotlinx.io.write
import kotlinx.io.writeString
import kotlin.math.min

internal const val PACKET_SIZE = 64

internal fun encode(message: Message<*, *>): ByteString = encodeBytes(
    message = UnsafeByteStringOperations.wrapUnsafe(message.encode()),
    messageType = message.toMessageType()
)

internal fun decodeHeader(
    source: Source,
    sink: Sink,
): Pair<Short, Int> {
    require(source.readByte() == '?'.code.toByte())
    require(source.readByte() == '#'.code.toByte())
    require(source.readByte() == '#'.code.toByte())
    val msgType = source.readShort()
    val msgSize = source.readInt()

    if (msgSize <= PACKET_SIZE - 9) {
        sink.write(source, msgSize.toLong())
    } else {
        sink.transferFrom(source)
    }

    return msgType to msgSize
}

internal fun isHeader(
    source: Source,
): Boolean = source.peek().let {
    it.exhausted().not() &&
            it.readByte() == '?'.code.toByte() &&
            it.exhausted().not() &&
            it.readByte() == '#'.code.toByte() &&
            it.exhausted().not() &&
            it.readByte() == '#'.code.toByte()
}

internal fun decodeData(
    source: Source,
    sink: Sink,
) {
    while (source.exhausted().not()) {
        require(source.readByte() == '?'.code.toByte())
        if (source.request(PACKET_SIZE - 1L)) {
            sink.write(source, PACKET_SIZE - 1L)
        } else {
            sink.transferFrom(source)
        }
    }
}

internal fun decode(
    msgType: Short,
    data: Source,
): Message<*, *> {
    val messageType = checkNotNull(MessageType.fromValue(msgType.toInt())) {
        "Unknown message type $msgType"
    }

    return messageType.toAdapter().decode(data.readByteArray())
}

private fun encodeBytes(
    message: ByteString,
    messageType: MessageType,
): ByteString {
    val msgType = messageType.value
    val data = Buffer().apply { write(message) }
    val buffer = Buffer()

    buffer.writeString("?##")
    buffer.writeShort(msgType.toShort())
    buffer.writeInt(message.size)
    buffer.write(data, min(PACKET_SIZE - 9L, message.size.toLong()))

    while (data.exhausted().not()) {
        buffer.writeString("?")
        buffer.write(data, min(PACKET_SIZE - 1L, data.size))
    }

    return buffer.readByteString()
}
