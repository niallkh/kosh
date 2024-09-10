package kosh.libs.trezor

import com.satoshilabs.trezor.lib.protobuf.MessageType
import com.squareup.wire.Message
import okio.Buffer
import okio.BufferedSink
import okio.BufferedSource
import okio.ByteString
import kotlin.math.min

internal const val PACKET_SIZE = 64

internal fun encode(message: Message<*, *>): ByteString = encodeBytes(
    message = message.encodeByteString(),
    messageType = message.toMessageType()
)

internal fun decodeHeader(
    source: BufferedSource,
    sink: BufferedSink,
): Pair<Short, Int> {
    require(source.readByte() == '?'.code.toByte())
    require(source.readByte() == '#'.code.toByte())
    require(source.readByte() == '#'.code.toByte())
    val msgType = source.readShort()
    val msgSize = source.readInt()

    if (msgSize <= PACKET_SIZE - 9) {
        sink.write(source, msgSize.toLong())
    } else {
        sink.writeAll(source)
    }

    return msgType to msgSize
}

internal fun isHeader(
    source: BufferedSource,
): Boolean = source.peek().let {
    it.exhausted().not() &&
            it.readByte() == '?'.code.toByte() &&
            it.exhausted().not() &&
            it.readByte() == '#'.code.toByte() &&
            it.exhausted().not() &&
            it.readByte() == '#'.code.toByte()
}

internal fun decodeData(
    source: BufferedSource,
    sink: BufferedSink,
) {
    while (source.exhausted().not()) {
        require(source.readByte() == '?'.code.toByte())
        if (source.request(PACKET_SIZE - 1L)) {
            sink.write(source, PACKET_SIZE - 1L)
        } else {
            sink.writeAll(source)
        }
    }
}

internal fun decode(
    msgType: Short,
    data: BufferedSource,
): Message<*, *> {
    val messageType = checkNotNull(MessageType.fromValue(msgType.toInt())) {
        "Unknown message type $msgType"
    }

    return messageType.toAdapter().decode(data)
}

private fun encodeBytes(
    message: ByteString,
    messageType: MessageType,
): ByteString {
    val msgType = messageType.value
    val data = Buffer().apply { write(message) }
    val buffer = Buffer()

    buffer.writeUtf8("?##")
    buffer.writeShort(msgType)
    buffer.writeInt(message.size)
    buffer.write(data, min(PACKET_SIZE - 9L, message.size.toLong()))

    while (data.exhausted().not()) {
        buffer.writeUtf8("?")
        buffer.write(data, min(PACKET_SIZE - 1L, data.size))
    }

    return buffer.readByteString()
}
