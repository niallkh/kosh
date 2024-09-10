package kosh.eth.abi.hra

//object HumanReadableAbi {
//
//    fun parse(hra: String): Abi.Item {
//        val buffer = Buffer().apply { write(hra.encodeUtf8()) }
//
//        val kind = buffer.kindOf()
//        val name = buffer.readUntil('('.code.toByte()).trim()
//
//
//
//
//        when (kind) {
//            Abi.Item.Kind.Function,
//            Abi.Item.Kind.Event,
//            Abi.Item.Kind.Error -> {
//                buffer.readUntil('('.code.toByte())
//            }
//
//            Abi.Item.Kind.Constructor,
//            Abi.Item.Kind.Fallback,
//            Abi.Item.Kind.Receive -> null
//        }
//    }
//
//    private fun Buffer.kindOf() = when {
//        startsWith("function") -> Abi.Item.Kind.Function
//        startsWith("event") -> Abi.Item.Kind.Event
//        startsWith("error") -> Abi.Item.Kind.Error
//        startsWith("constructor") -> Abi.Item.Kind.Constructor
//        startsWith("fallback") -> Abi.Item.Kind.Fallback
//        startsWith("receive") -> Abi.Item.Kind.Receive
//        else -> error("Invalid hra")
//    }
//
//    private fun Buffer.startsWith(str: String): Boolean {
//        return (peek().readUtf8(str.length.toLong()) == str).also {
//            if (it) {
//                skip(str.length.toLong())
//            }
//        }
//    }
//
//    private fun Buffer.readUntil(symbol: Byte): String {
//        val peek = peek()
//        var length = 0L
//        while (peek.readByte() != symbol) {
//            length++
//        }
//        return buffer.readUtf8(length)
//    }
//}
