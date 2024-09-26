package kosh.libs.ledger


//internal fun encodeBytes(
//    message: ByteString,
//    channel: Int,
//): ByteString {
//    val data = Buffer().apply {
//        writeShort(message.size.toShort())
//        write(message)
//    }
//    val buffer = Buffer()
//
//    var counter = 0
//    while (data.exhausted().not()) {
//        buffer.writeShort(channel.toShort())
//        buffer.writeByte(TAG.toByte())
//        buffer.writeShort(counter.toShort())
//        buffer.write(data, min(PACKET_SIZE - 5L, data.size))
//        counter++
//    }
//
//    return buffer.readByteString()
//}
//
//internal fun isHeader(
//    source: Source,
//    channel: Int,
//): Boolean = source.peek().let {
//    it.exhausted().not() &&
//            it.readShort() == channel.toShort() &&
//            it.readByte() == TAG.toByte() &&
//            it.readShort() == 0.toShort()
//}
//
//internal fun decodeHeader(
//    source: Source,
//    sink: Sink,
//    channel: Int,
//): Short {
//    require(source.readShort() == channel.toShort())
//    require(source.readByte() == TAG.toByte())
//    require(source.readShort() == 0.toShort())
//
//    val msgSize = source.readShort()
//
//    if (msgSize <= PACKET_SIZE - 7) {
//        sink.write(source, msgSize.toLong())
//    } else {
//        sink.transferFrom(source)
//    }
//
//    return msgSize
//}
//
//internal fun decodeData(
//    source: Source,
//    sink: Sink,
//    channel: Int,
//) {
//    var counter = 1
//    while (source.exhausted().not()) {
//        require(source.readShort() == channel.toShort())
//        require(source.readByte() == TAG.toByte())
//        require(source.readShort() == counter.toShort())
//
//        if (source.request(PACKET_SIZE - 5L)) {
//            sink.write(source, PACKET_SIZE - 5L)
//        } else {
//            sink.transferFrom(source)
//        }
//        counter++
//    }
//}
//
//
