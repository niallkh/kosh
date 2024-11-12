package kosh.libs.keystone.coder

import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString

private const val MIN_BYTEWORDS =
    "aeadaoaxaaahamatayasbkbdbnbtbabsbebybgbwbbbzcmchcscfcycwcecackctcxclcpcndkdadsdidedtdrdndwdpdmdldyeheyeoeeecenemetesftfrfnfsfmfhfzfpfwfxfyfefgflfdgagegrgsgtglgwgdgygmgughgohfhghdhkhthphhhlhyhehnhsidiaieihiyioisinimjejzjnjtjljojsjpjkjykpkoktkskkknkgkekikblblalylflslrlplnltloldlelulklgmnmymhmemomumwmdmtmsmknlnyndnsntnnnenboyoeotoxonolospdptpkpypspmplpepfpaprqdqzrerprlrorhrdrkrfryrnrsrtsesasrssskswstspsosgsbsfsntotktitttdtetytltbtstptatnuyuoutueurvtvyvovlvevwvavdvswlwdwmwpwewywswtwnwzwfwkykynylyaytzszoztzczezm"

object Bytewords {

    fun decodeMinimal(encoded: String): ByteString = encoded
        .asSequence()
        .chunked(2)
        .map { "${it[0]}${it[1]}" }
        .map { word -> indexOf(word) }
        .fold(Buffer()) { acc, byte -> acc.apply { writeByte(byte.toByte()) } }
        .readByteString()

    private fun indexOf(word: String): Int {
        var index = -1
        do {
            index = MIN_BYTEWORDS.indexOf(word, index + 1, ignoreCase = true)
                .takeIf { it != -1 }
                .let(::checkNotNull)
        } while (index % 2 == 1)

        return index / 2
    }

    fun encodeMinimal(body: ByteString): String = buildString(body.size * 2) {
        repeat(body.size) {
            val index = body[it].toUByte().toUInt().toInt()
            append(MIN_BYTEWORDS[index * 2])
            append(MIN_BYTEWORDS[index * 2 + 1])
        }
    }
}
