package kosh.ui.navigation

object UrlDecoder {
    fun decode(source: String, plusToSpace: Boolean = false): String {
        if (source.isEmpty()) {
            return source
        }

        val length = source.length
        val out = StringBuilder(length)
        var bytesBuffer: ByteArray? = null
        var bytesPos = 0
        var i = 0
        var started = false
        while (i < length) {
            val ch = source[i]
            if (ch == '%') {
                if (!started) {
                    out.append(source, 0, i)
                    started = true
                }
                if (bytesBuffer == null) {
                    bytesBuffer = ByteArray((length - i) / 3)
                }
                i++
                require(length >= i + 2) { "Incomplete trailing escape ($ch) pattern" }
                try {
                    val v = source.substring(i, i + 2).toInt(16)
                    require(v in 0..0xFF) { "Illegal escape value" }
                    bytesBuffer[bytesPos++] = v.toByte()
                    i += 2
                } catch (e: NumberFormatException) {
                    throw IllegalArgumentException(
                        "Illegal characters in escape sequence: $e.message",
                        e
                    )
                }
            } else {
                if (bytesBuffer != null) {
                    out.append(bytesBuffer.decodeToString(0, bytesPos))
                    started = true
                    bytesBuffer = null
                    bytesPos = 0
                }
                if (plusToSpace && ch == '+') {
                    if (!started) {
                        out.append(source, 0, i)
                        started = true
                    }
                    out.append(" ")
                } else if (started) {
                    out.append(ch)
                }
                i++
            }
        }

        if (bytesBuffer != null) {
            out.append(bytesBuffer.decodeToString(0, bytesPos))
        }

        return if (!started) source else out.toString()
    }
}
